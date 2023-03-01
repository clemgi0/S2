#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
    fork();
    if (fork()) {
        fork();
    }

    printf("%d : A\n", getpid());

    return EXIT_SUCCESS;
}