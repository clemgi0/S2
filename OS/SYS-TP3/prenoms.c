#include <assert.h>
#include <ctype.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <unistd.h>

typedef struct {
    int sexe; // 1=Garcon, 2=Fille
    char prenom[25];
    int annee; // 1900..2021
    int nombre; // d'enfants nés cette année avec ce prénom
} tuple;

struct stat buf;


int main(int argc, char **argv)
{

    int fd = open("prenoms.dat", O_RDWR);
    fstat(fd, &buf);

    tuple * personnes = mmap(NULL, buf.st_size, PROT_READ|PROT_WRITE, MAP_SHARED|MAP_FILE, fd, 0);

    int nbTuples = (int)buf.st_size/(int)sizeof(tuple);
    printf("Nombre de tuples : %d\n", nbTuples);

    int sizeLongestName = 0;
    char * longestName;
    int yearSwan = 0;
    int nbSwans = 0;
    for (int i=0; i<nbTuples; ++i) {

        for (int j=1; j<strlen(personnes[i].prenom); ++j) {
            personnes[i].prenom[j] = tolower(personnes[i].prenom[j]);
        }
        

        if (strlen(personnes[i].prenom) >= sizeLongestName) {
            sizeLongestName = strlen(personnes[i].prenom);
            longestName = personnes[i].prenom;
        }
        if (strcmp(personnes[i].prenom, "Swan") == 0 && personnes[i].nombre > nbSwans) {
            yearSwan = personnes[i].annee;
            nbSwans = personnes[i].nombre;
        }
    }

    printf("Plus grand prénom : %s\n", longestName);

    printf("En %d il y a eu %d Swan !\n", yearSwan, nbSwans);

    return 0;
}
