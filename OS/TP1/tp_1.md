# TP 1 - Appels systèmes sous Unix

> Le but de ce TP est d’implémenter en C un programme qu’on nommera parexec .

### Useful links

[nanosleep function](https://timmurphy.org/2009/09/29/nanosleep-in-c-c/)

##### make & Makefile

[Makefile full tuto](https://makefiletutorial.com/#-delete_on_error) ⭐️

[Makefile cheatsheet](https://cheatography.com/bavo-van-achte/cheat-sheets/gnumake/) ⭐️

[.PRECIOUS, What causes Make to delete intermediate files ? | StackExchange](https://unix.stackexchange.com/questions/517190/what-causes-make-to-delete-intermediate-files)

[Using target name in Makefile prequisite ? | StackOverflow](https://stackoverflow.com/questions/37933724/target-name-in-makefile-prerequisite)

[What is the difference between %.c and *.c in GNU Make](https://stackoverflow.com/questions/31652993/what-is-the-difference-between-c-and-c-in-gnu-make)

[`$@, $%, $<, $?, $^, $+, $*` Automatic Variables](https://www.gnu.org/software/make/manual/html_node/Automatic-Variables.html)

### Travail préliminaire

`tar -zxvf SYS-TP1.tgz` : Dézipper les fichiers fournis.

### notes

##### assertion or normal error handling ?

Assertions are statements used to test assumptions made by programmer.

> **WARN :** Unlike normal error handling, assertions are generally disabled at run-time.  Therefore, it is not a good idea to write statements in assert() that can cause side effects.

In C/C++, we can completely remove assertions at compile time using the preprocessor NODEBUG.

### Questions

##### exercice.c

On a le programme suivant :

```
#include <stdio.h>
#include <sys/types.h> // fork()
#include <unistd.h> // pid_t (int)*

int main(void)
{   
    fork();
    if ( fork() )
    {
        fork();
    }
    printf("%d: A\r\n", getpid());
    return 0;
}
```

> `fork()` permet de dupliquer deux processus,
> qu’on appelle respectivement le «parent» et «l’enfant», qui exécuteront chacun une copie indépendante du
> même programme. Dans le parent, la valeur de retour de fork() est le PID de l’enfant. Dans l’enfant, la valeur de retour de fork() est zéro.

Après exécution, on obtient toujouts quelque chose de la forme :

```shell
onyr@aezyr:~/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1$ ./bin/exercice 
22718: A
22719: A
22721: A
22723: A
22720: A
22722: A
```

On en déduit l'ordre d'éxécution en fonction de l'ordre d'affichage. Le parent est toujours exécuté en premier, puis on remonte le programme, `fork()` après `fork()` dans l'ordre inverse de celui du programme.

![1613665485123.png](.vscode/img/1613665485123.png)

##### exercice1.c

On a le programme suivant :

```shell
#include <unistd.h>

int main(void)
{
    printf("%d: A\r\n", getpid());

    execl("/bin/ls", "ls", "-l", ".", NULL);

    printf("%d: A\r\n", getpid());
    return 0;
}
```

> Pour demander au noyau de changer le programme en cours d’exécution, on invoque l’appel système
> `exec`. Cet appel ne retourne jamais : au contraire, le processus oublie tout ce
> qu’il était en train de faire, et commence à exécuter le nouveau programme depuis le début. Attention il
> ne s’agit pas d’un oubli temporaire : lorsque notre nouveau programme se termine, notre processus se
> termine aussi, et on ne reviendra donc jamais au programme «précédent».

Après exécution, on obtient :

```shell
26924: A
total 2852
drwxrwxr-x 2 onyr onyr    4096 févr. 18 17:34 bin
-rw-rw-r-- 1 onyr onyr 1447839 févr. 18 17:30 exercice.kra
-rw-rw-r-- 1 onyr onyr 1447180 févr. 18 17:30 exercice.kra~
-rw-r--r-- 1 onyr onyr     787 févr. 18 17:33 Makefile
drwxrwxr-x 2 onyr onyr    4096 févr. 18 17:34 obj
drwxrwxr-x 2 onyr onyr    4096 févr. 18 15:46 old
drwxrwxr-x 2 onyr onyr    4096 févr. 18 17:32 src
```

On remarque qu'un seul A a été affiché. En effet, le second se trouvant après l'appel de `execl`, le processus axécute le code de `ls` sans revenir au `main()` de `exercice1.c`.

##### parexec.c

Implémentation initiale de `parexec`.

`./bin/parexec [COMMAND] [ARGS...]` : run the `parexec` command, specify a command as well as a list of arguments.

`./bin/parexec /home/onyr/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep 2 3 4 5` : example of use of the command `parexec` on the command `rebours_sleep`.

```shell
onyr@aezyr:~/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1$ ./bin/parexec /home/onyr/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep 2 3 4 5
[argument index : 0] ./bin/parexec
[argument index : 1] /home/onyr/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep
[argument index : 2] 2
[argument index : 3] 3
[argument index : 4] 4
[argument index : 5] 5
[argument index : 0] rebours_sleep
[argument index : 1] 2
31383: 2
[argument index : 0] rebours_sleep
[argument index : 1] 5
31386: 5
[argument index : 0] rebours_sleep
[argument index : 1] 4
31385: 4
[argument index : 0] rebours_sleep
[argument index : 1] 3
31384: 3
31383: 1
31386: 4
31385: 3
31384: 2
31383: fin
31386: 3
31385: 2
31384: 1
31386: 2
31385: 1
31384: fin
31386: 1
31385: fin
31386: fin
```


##### parexec1.c

Variation de `parexec`. Il prend un argument supplémentaire N entre prog et argument1 . Cet argument sera un entier indiquant le nombre maximum d’instances de prog à lancer en parallèle. Lorsque ce nombre est atteint, parexec  doit attendre qu’un de ses fils se termine avant d’en lancer un nouveau.

`./bin/parexec1 /home/onyr/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep 2 3 4 5`

```shell
onyr@aezyr:~/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1$ ./bin/parexec1 /home/onyr/Documents/3if/s2/os_syys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep 2 3 4 5
[argument index : 0] ./bin/parexec1
[argument index : 1] /home/onyr/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep
[argument index : 2] 2
[argument index : 3] 3
[argument index : 4] 4
[argument index : 5] 5
[argument index : 0] rebours_sleep
[argument index : 1] 3
34394: 3
[argument index : 0] rebours_sleep
[argument index : 1] 4
34395: 4
34394: 2
34395: 3
34394: 1
34395: 2
34394: fin
34395: 1
[argument index : 0] rebours_sleep
[argument index : 1] 5
34396: 5
34395: fin
34396: 4
34396: 3
34396: 2
34396: 1
34396: fin
```


##### Arrêts intempestifs et signaux UNIX

Ouvrez deux fenêtres de terminal. Dans la première exécutez ./rebours 10 et repérez le
PID du processus, par exemple 12345. Dans la seconde fenêtre tapez kill 12345 et constatez que
le compte à rebours est alors interrompu instantanément.

```shell
onyr@aezyr:~/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1$ ./bin/rebours_sleep 20
[argument index : 0] ./bin/rebours_sleep
[argument index : 1] 20
34475: 20
34475: 19
34475: 18
34475: 17
34475: 16
34475: 15
Terminated
```

```shell
onyr@aezyr:~/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin$ kill 34475
```

`kill -l`

```shell
onyr@aezyr:~/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/simple_programs$ kill -l
 1) SIGHUP	 2) SIGINT	 3) SIGQUIT	 4) SIGILL	 5) SIGTRAP
 6) SIGABRT	 7) SIGBUS	 8) SIGFPE	 9) SIGKILL	10) SIGUSR1
11) SIGSEGV	12) SIGUSR2	13) SIGPIPE	14) SIGALRM	15) SIGTERM
16) SIGSTKFLT	17) SIGCHLD	18) SIGCONT	19) SIGSTOP	20) SIGTSTP
21) SIGTTIN	22) SIGTTOU	23) SIGURG	24) SIGXCPU	25) SIGXFSZ
26) SIGVTALRM	27) SIGPROF	28) SIGWINCH	29) SIGIO	30) SIGPWR
31) SIGSYS	34) SIGRTMIN	35) SIGRTMIN+1	36) SIGRTMIN+2	37) SIGRTMIN+3
38) SIGRTMIN+4	39) SIGRTMIN+5	40) SIGRTMIN+6	41) SIGRTMIN+7	42) SIGRTMIN+8
43) SIGRTMIN+9	44) SIGRTMIN+10	45) SIGRTMIN+11	46) SIGRTMIN+12	47) SIGRTMIN+13
48) SIGRTMIN+14	49) SIGRTMIN+15	50) SIGRTMAX-14	51) SIGRTMAX-13	52) SIGRTMAX-12
53) SIGRTMAX-11	54) SIGRTMAX-10	55) SIGRTMAX-9	56) SIGRTMAX-8	57) SIGRTMAX-7
58) SIGRTMAX-6	59) SIGRTMAX-5	60) SIGRTMAX-4	61) SIGRTMAX-3	62) SIGRTMAX-2
63) SIGRTMAX-1	64) SIGRTMAX
```


##### parexec2

Modification de `parexec1` pour que, si une des instances de prog se termine anormalement (i.e. sur un signal) alors il ne lance plus de nouvelles instances de prog . À la place, il attend que les processus déjà lancés se terminent puis il quitte à son tour.

`./bin/parexec2 /home/onyr/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep 2 20 30 40`

`kill -s SIGTERM [PID]` : kill the process with specified `PID`.

On lance la commande 

```shell
onyr@aezyr:~/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin$ kill -s SIGTERM 36039
```

On remarque ligne 23-25 que l'un des childprocesses a été interrompu. Le programme termine alors le childprocess qui reste et arrête ensuite le programme.

```shell
onyr@aezyr:~/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1$ ./bin/parexec2 /home/onyr/Documents/3if/s2/os_syys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep 2 20 30 40
[argument index : 0] ./bin/parexec2
[argument index : 1] /home/onyr/Documents/3if/s2/os_sys_exploitation/tp_1/SYS-TP1/bin/rebours_sleep
[argument index : 2] 2
[argument index : 3] 20
[argument index : 4] 30
[argument index : 5] 40
[argument index : 0] rebours_sleep
[argument index : 1] 20
36039: 20
[argument index : 0] rebours_sleep
[argument index : 1] 30
36040: 30
36039: 19
36040: 29
36039: 18
36040: 28
36039: 17
36040: 27
36039: 16
36040: 26
36039: 15
36040: 25
36039: 14
36040: 24
36040: 23
36040: 22
36040: 21
36040: 20
36040: 19
36040: 18
36040: 17
36040: 16
36040: 15
36040: 14
36040: 13
36040: 12
36040: 11
36040: 10
36040: 9
36040: 8
36040: 7
36040: 6
36040: 5
36040: 4
36040: 3
36040: 2
36040: 1
36040: fin
```
