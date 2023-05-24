#include <assert.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>

int main(int argc, char* argv[])
{
    for(int i=1; i<argc ; i++)
    {
        FILE* stream = fopen(argv[i], "r");
        assert(stream != NULL);

        while (!feof(stream)) {
            putchar(fgetc(stream));
        }

        fclose(stream);
    }    

    return 0;
}
