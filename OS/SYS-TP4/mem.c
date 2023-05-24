#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <assert.h>
#include <unistd.h>
#include <sys/mman.h>

#include "mem.h"

void * heap_base = NULL;// first address of the heap
void * heap_end  = NULL;// first address beyond the heap

int mem_initialized = 0;

// initialize the memory manager
void mem_init(void)
{
    // request memory from the kernel
    heap_base = mmap(NULL, 800, PROT_READ|PROT_WRITE,MAP_PRIVATE|MAP_ANONYMOUS,-1,0);
    assert(heap_base != MAP_FAILED);

    heap_end = heap_base + 800 ;

    *( (int64_t*)heap_base ) = 800; // HEADER
    *( (int64_t*)(heap_end - 8) ) = 800; // FOOTER
    
    mem_initialized = 1;
}

void * mem_alloc(int64_t length)
{
    assert(mem_initialized == 1);
    
    // compute actual size of block
    length = (length+7)/8*8 ; // round up to nearest multiple of 8
    length += 16;              // add space for the header and footer

    // heap traversal
    void *  block_ptr ;
    int64_t header ;
    int64_t size;   
    char    flags;
    
    block_ptr = heap_base;
    while(block_ptr < heap_end)
    {
        header = *( (int64_t*)block_ptr );
        flags  = header & 0b111;  // keep only three least significant bits
        size = header & ~0b111;   // discard the three least significant bits

        if( (flags == 0 ) &&      // if current block is free, and
            (size >= length))     // is also large enough, then we have a winner
            break;

        // otherwise we move on to the next block
        block_ptr += size;
    }

    // if the heap  traversal reached this far, then it  means we have
    // found no suitable block, so we should return NULL
    if(block_ptr >= heap_end)
    {
        return NULL;
    }

    flags = 0b001; // mark block as taken
    header = length | flags;
    *( (int64_t*)block_ptr ) = header ; // write header back into the block
    *( (int64_t*)(block_ptr+length-8)) = header; // write footer at the end of the block

    void * new_block_pointer = block_ptr + length;
    int64_t new_size = size - length;
    *( (int64_t*)new_block_pointer) = new_size;
    *( (int64_t*)(new_block_pointer + new_size - 8)) = new_size;

    return block_ptr + 8 ; // skip header
}

void mem_release(void *ptr)
{
    assert( mem_initialized == 1);
    assert( ((int64_t)ptr % 8) == 0 ); // sanity check

    int64_t  header;
    int64_t  size;
    int64_t  new_size;
    int64_t  previous_footer;
    int64_t  previous_size;
    int64_t  previous_flags;
    int64_t  next_header;
    int64_t  next_size;
    int64_t  next_flags;

    void * block_ptr = ptr - 8;
    header = *((int64_t*)block_ptr);
    size = header & ~0b111;
    previous_footer = (block_ptr == heap_base) ? -1 : *((int64_t*)(block_ptr - 8));
    previous_size = previous_footer & ~0b111;
    previous_flags = previous_footer & 0b111;
    next_header = (block_ptr+size == heap_end) ? -1 : *((int64_t*)(block_ptr + size));
    next_size = next_header & ~0b111;
    next_flags = next_header & 0b111;
    

    if (previous_footer != -1 && next_header != -1 && !previous_flags && !next_flags) {
        void * previous_block_ptr = block_ptr - previous_size;
        new_size = previous_size + size + next_size;

        *( (int64_t*)previous_block_ptr ) = new_size;
        *( (int64_t*)(previous_block_ptr + new_size - 8)) = new_size;
    }
    else if (previous_footer != -1 && !previous_flags) {
        void * previous_block_ptr = block_ptr - previous_size;
        new_size = previous_size + size;
        *( (int64_t*)previous_block_ptr ) = new_size;
        *( (int64_t*)(previous_block_ptr + new_size - 8)) = new_size; 
    }
    else if (next_header != -1 && !next_flags) {
        new_size = size + next_size;
        *( (int64_t*)block_ptr ) = new_size;
        *( (int64_t*)(block_ptr + new_size - 8)) = new_size; 
    }
    else {
       *( (int64_t*)block_ptr ) = size;
       *( (int64_t*)(block_ptr + size - 8)) = size; 
    }

    

    return;
}

void mem_show_heap(void)
{
    assert( mem_initialized == 1);
    
    void * block_ptr = heap_base;

    printf("heap_base = %p\n",heap_base);
    while(block_ptr < heap_end)
    {
        int64_t header = *( (int64_t*)block_ptr );
        char    flags  = header & 0b111;  //   keep only three least significant bits
        int64_t size   = header & ~0b111; // discard the three least significant bits
        int64_t footer = *( (int64_t*)(block_ptr + size - 8));
        assert(header == footer);
        if( (size < 16) ||
            (size%8 != 0) )
        {
            printf("error: block at %p has incorrect size %ld\n",block_ptr,size);
            exit(1);
        }

        printf("  block at %p: header=0x%08lx footer=0x%08lx size=%ld flags=%d (%s)\n",
               block_ptr,header, footer, size,flags,
               (flags ? "taken" : "free")
               );

        block_ptr += size; // move on to next block
    }

    printf("heap_end = %p\n",heap_end);

    //sanity check: a full heap traversal should reach *exactly* the end
    assert( block_ptr == heap_end); 
}
