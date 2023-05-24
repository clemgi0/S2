#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <unistd.h>

void afficher(char* crible, int n)
{
    printf("Nombres premiers jusqu'à %d : ", n);
    for (int i=2; i<n; ++i) {
        if (crible[i])
            printf("%d, ", i);
    }
    printf("\n");
}

void rayer_multiples(char* crible, int n, int k)
{
    for (int i = k+1; i<n; ++i) {
        if (i%k == 0)
            crible[i] = 0;
    }
}


int main(int argc, char **argv)
{
    int n=1000;
    if(argc>1)
    {
        n = atoi(argv[1]);
        assert( n > 0 );
    }
    
    char * crible = mmap(NULL, n*sizeof(int), PROT_READ|PROT_WRITE, MAP_ANONYMOUS|MAP_SHARED, 0, 0);
    assert(crible != MAP_FAILED);

    for(int i=0; i<n; i++)
        crible[i] = 1;
    
    for(int k=2; k<n; k++) {
        if (!fork()) {
            rayer_multiples(crible, n, k);
            exit(EXIT_SUCCESS);
        }
    }

    for (int k=2; k<n; k++)
        wait(NULL);

    afficher(crible, n);
    
    return 0;
}
