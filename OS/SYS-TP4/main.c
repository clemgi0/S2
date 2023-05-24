#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <assert.h>

#include "mem.h"

int main(int argc, char *argv[])
{
    char * p;
    mem_init();

    p = mem_alloc( 80 ); 
    assert( p != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p);
    char * p2 = mem_alloc( 80 ); 
    assert( p2 != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p2);
    p = mem_alloc( 80 ); 
    assert( p != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p);
    mem_show_heap();
    mem_release(p2);
    mem_show_heap();
    mem_release(p);
    mem_show_heap();
}
