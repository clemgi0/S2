#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv)
{
    printf("A\n");
    execl("./rebours", "./rebours", "5", NULL);
    printf("A\n");

    return EXIT_SUCCESS;
}
