#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv)
{
    int duration = 0;

    if (argc > 1) {
        duration = atoi(argv[1]);
        assert(duration > 0);
    }

    for (int i = duration; i > 0; --i) {
        printf("%d : %d\n", getpid(), i);
        sleep(1);
    }

    printf("%d : fin\n", getpid());

    return EXIT_SUCCESS;
}
