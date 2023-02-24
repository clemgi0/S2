#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv)
{
    printf("%d : hello world\n", getpid());

    if (fork()) {
        printf("%d : je suis le parent\n", getpid());
    }
    else {
        printf("%d : je suis l'enfant\n", getpid());
    }



    return EXIT_SUCCESS;
}
