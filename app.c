#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
    printf("Fork 1\n");
    fork();
    printf("Fork 2\n");
    if (fork()) {
        printf("Fork 3\n");
        fork();
    }

    printf("%d : A\n", getpid());

    return EXIT_SUCCESS;
}