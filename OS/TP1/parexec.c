#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char** argv)
{
    printf("Bonjour je suis '%s'\n",argv[0]);

    int nbProcess = 0;
    int statusCode;
    for(int i = 3 ; i<argc ; i++)
    {
        if(fork() == 0)
        {
            execl(argv[1], argv[1], argv[i], NULL);
        }
        nbProcess++;

        if (nbProcess == atoi(argv[2])) {
            wait(&statusCode);
            nbProcess--;
            if (!WIFEXITED(statusCode))
                break;
        }
    }
    
    for(int i = 0 ; i<nbProcess ; i++)
        wait(NULL);
    
    return EXIT_SUCCESS;
}
