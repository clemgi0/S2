#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char** argv)
{
    printf("Bonjour je suis '%s'\n",argv[0]);

    for(int i = 2 ; i<argc ; i++)
    {
        if(fork() == 0)
        {
            execl(argv[1], argv[1], argv[i], NULL);
        }
    }
    
    for(int i = 2 ; i<argc ; i++)
        wait(NULL);
    
    return EXIT_SUCCESS;
}
