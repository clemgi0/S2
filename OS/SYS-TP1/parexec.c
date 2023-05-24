#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char** argv)
{
    assert(argc >= 4);
    char * prog = argv[1];
    int N = atoi(argv[2]);
    int progs = 0;
    int end_status;
    int progList[N];
    
    for (int i=3; i<argc; ++i) {
        int pid = fork();
        if (!pid)
            execl(prog, prog, argv[i], NULL);

        progList[progs] = pid;

        if (++progs == N) {
            wait(&end_status);
            
            --progs;
            if (WIFSIGNALED(end_status))
                break;
        }
    }

    for (int i=0; i<progs; ++i)
        wait(NULL);

    return EXIT_SUCCESS;
}
