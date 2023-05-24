#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv)
{
    if (fork()) {
        if (fork()) {
            execl("./rebours", "./rebours", "2", NULL);
        }
        execl("./rebours", "./rebours", "4", NULL);
    }
    

    return EXIT_SUCCESS;
}
