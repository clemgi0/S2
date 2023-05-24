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
        int fd = open(argv[i], O_RDONLY);
        assert(fd != -1);

        char c;
        while (read(fd, &c, sizeof(c)) != 0) {
            write(STDOUT_FILENO, &c, sizeof(c));
        }
        

        close(fd);
    }    

    return 0;
}
