#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv)
{
    assert(argc == 2);
    assert(argv[1] > 0);

    int temps = atoi(argv[1]);

    printf("%d: debut\n", getpid());

    while (temps > 0) {
        printf("%d: %d\n", getpid(), temps--);
        sleep(1);
    }
     printf("%d: fin\n", getpid());

    return EXIT_SUCCESS;
}
