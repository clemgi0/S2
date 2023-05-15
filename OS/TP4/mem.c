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

    // create some free blocks: five of 80 bytes and one of 400 bytes
    void *block_ptr = heap_base;
    *((int64_t*)block_ptr) = 800;
    block_ptr += 792;
    *((int64_t*)block_ptr) = 800;
    block_ptr -= 792;
    
    mem_initialized = 1;
}

void * mem_alloc(int64_t length)
{
    assert(mem_initialized == 1);
    
    // compute actual size of block
    length = (length+7)/8*8 ; // round up to nearest multiple of 8
    length += 16;              // add space for the header

    // heap traversal
    void *  block_ptr ;
    int64_t header;
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
    if(size - length > 16){
        header = length | flags;
        block_ptr += length; //début nouveau block
        *((int64_t*)block_ptr) = size - length; // on y écris sa taille dans le header
        block_ptr += size - length - 8; // pareil mais dans le footer
        *((int64_t*)block_ptr) = size - length; // genre on écris la 
        block_ptr -= size + 8; // on revient au début du header du 1er block
    }else{
        header = size | flags;
    }

    *( (int64_t*)block_ptr ) = header ; // write header back into the block
    block_ptr += length - 8;
    *( (int64_t*)block_ptr ) = header ; // write footer back into the block
    block_ptr -= length - 8;

    printf("block_ptr address : %p\r\n block_ptr value : %ld\r\n", block_ptr, *((int64_t*)block_ptr));

    return block_ptr + 8 ; // skip header
}

void mem_release(void *ptr)
{
    assert( mem_initialized == 1);
    assert( ((int64_t)ptr % 8) == 0 ); // sanity check

    // TODO: your deallocation algorithm goes here
    ptr -= 8;

    // printf("address of ptr : %p\n\r", ptr);

    int64_t header = *((int64_t*)ptr);
    int64_t flags = 0b111;
    // printf("header = %ld et flags = %ld\r\n", header, flags);
    header = header & ~flags;
    // printf("header = %ld\r\n", header);

    *((int64_t*)ptr) = header;

    // printf("value of header : %ld\n\r", *((int64_t*)ptr));


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
        block_ptr += size - 8;
        int64_t footer = *( (int64_t*)block_ptr );
        block_ptr -= size - 8;
        printf("size = %ld footer = 0x%08lx\n", size, footer);
        if( (size < 8) ||
            (size%8 != 0) )
        {
            printf("error: block at %p has incorrect size %ld\n",block_ptr,size);
            exit(1);
        }

        printf("  block at %p: header=0x%08lx footer=0x%08lx size=%ld flags=%d (%s)\n",
               block_ptr,header,footer,size,flags,
               (flags ? "taken" : "free")
               );

        block_ptr += size; // move on to next block
    }

    printf("heap_end = %p\n",heap_end);

    //sanity check: a full heap traversal should reach *exactly* the end


    // ET LA CA BUG FAUT QUE JE CHECK CA 


    assert( block_ptr == heap_end); 
}
