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

        struct stat buf;
        fstat(fd, &buf);

        char * content = mmap(NULL, buf.st_size, PROT_READ, MAP_SHARED|MAP_FILE, fd, 0);

        write(STDOUT_FILENO, content, buf.st_size);

        munmap(content, buf.st_size);

        close(fd);
    }    

    return 0;
}