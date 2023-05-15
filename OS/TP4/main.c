#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <assert.h>

#include "mem.h"

int main(int argc, char *argv[])
{
    // initialize the allocator
    mem_init();
    mem_show_heap();
    
    char *p = mem_alloc( 42 ); 
    assert( p != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p);
    mem_show_heap();

    mem_init();
    mem_show_heap();
    
    char *p1 = mem_alloc( 42 );
    assert( p1 != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p1);
    mem_show_heap();
    char *p2 = mem_alloc( 42 ); 
    assert( p2 != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p2);
    mem_show_heap();
    char *p3 = mem_alloc( 42 ); 
    assert( p3 != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p3);
    mem_show_heap();
    char *p4 = mem_alloc( 42 ); 
    assert( p4 != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p4);
    mem_show_heap();
    char *p5 = mem_alloc( 42 ); 
    assert( p5 != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p5);
    mem_show_heap();
    char *p6 = mem_alloc( 42 ); 
    assert( p6 != NULL ); // check whether the allocation was successful
    printf("allocated 42 bytes at %p\n", p6);
    mem_show_heap();
    // char *p7 = mem_alloc( 42 ); 
    // assert( p7 != NULL ); // check whether the allocation was successful
    // printf("allocated 42 bytes at %p\n", p7);
    // mem_show_heap();

    mem_init();
    char *p8 = mem_alloc( 250 ); 
    assert( p8 != NULL ); // check whether the allocation was successful
    printf("allocated 250 bytes at %p\n", p8);
    mem_show_heap();
    mem_release(p8);
    printf("release 250 bytes at %p\n", p8);
    char *p9 = mem_alloc( 250 ); 
    assert( p9 != NULL ); // check whether the allocation was successful
    printf("allocated 250 bytes at %p\n", p9);
    mem_show_heap();

    mem_init();
    char *p10 = mem_alloc( 42 ); 
    assert( p10 != NULL ); // check whether the allocation was successful
    printf("allocated -42 bytes at %p\n", p10);
    mem_show_heap();
    char *p11 = mem_alloc( 0 ); 
    assert( p11 != NULL ); // check whether the allocation was successful
    printf("allocated 0 bytes at %p\n", p11);
    mem_show_heap();
    mem_release(p10);
    printf("release -42 bytes at %p\n", p10);
    mem_show_heap();

}
