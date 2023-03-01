#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <math.h>

typedef struct{
    int n; // number of vertices in the graph
    int* nbSucc; // for each 0<=i<n, nbSucc[i] = number of successors of i
    int** succ; // for each 0<=i<n and each 0<=j<nbSucc[i], succ[i][j]=jth successor of i
} DIGRAPH;

typedef struct{
    int page;
    double proba;
} Pair;

DIGRAPH* readDigraph(FILE *fp){
    // return the DIGRAPH contained in file fp
    DIGRAPH *g = (DIGRAPH*)malloc(sizeof(DIGRAPH));
    if (fscanf(fp, "%d\n", &(g->n)) != 1 || g->n <= 0){
        printf("erreur de lecture du fichier\n");
        exit(1);
    }
    g->nbSucc = (int*)malloc(g->n*sizeof(int));
    g->succ = (int**)malloc(g->n*sizeof(int*));
    int succ[g->n];
    for (int i=0; i<g->n; i++){
        g->nbSucc[i] = 0;
        while (1){
            if (fscanf(fp, "%d", &(succ[g->nbSucc[i]])) != 1 || succ[g->nbSucc[i]] >= g->n){
                printf("erreur de lecture du fichier\n");
                exit(1);
            }
            if (succ[g->nbSucc[i]]<0) break;
            g->nbSucc[i]++;
        };
        g->succ[i] = (int*)malloc(g->nbSucc[i]*sizeof(int));
        memcpy(g->succ[i], succ, g->nbSucc[i]*sizeof(int));
    }
    return g;
}

void printDigraph(DIGRAPH *g){
    // For each vertex of g, display the list of its successors
    for (int i=0; i<g->n; i++){
        printf("Vertex %d has %d successors: ", i, g->nbSucc[i]);
        for (int j=0; j<g->nbSucc[i]; j++)
            printf("%d ", g->succ[i][j]);
        printf("\n");
    }
}

void computeProba(double* sk, DIGRAPH* g, double alpha) {
    double* skprev = malloc(sizeof(double)*g->n);
    
    for(int i = 0 ; i<=g->n ; i++){
        skprev[i] = sk[i];
        sk[i] = 0;
    }

    double qabs = 0;
    for (int i = 0; i<g->n; i++)
        if (g->nbSucc[i] == 0)
            qabs += skprev[i];
    qabs /= g->n;

    double q = (1.-alpha) / g->n ;

    for(int i = 0 ; i<g->n ; i++) {
        for(int j = 0 ; j<g->nbSucc[i] ; j++)
            sk[g->succ[i][j]] += alpha * (skprev[i]) / (g->nbSucc[i]);

        sk[i] += alpha*qabs + q;
    }

    free(skprev);
}

void printsk(int k, double* sk, int size) {
    printf("s%d = ( ", k);
    double somme = 0;
    for (int i = 0; i < size; i++){
        printf("%lf ", sk[i]);
        somme += sk[i];
    }
    printf(") ; S%d = %lf\n", k, somme);
    
}

void findTopPages(double * sk, int size) {
    Pair topPages[5];
    for(int i = 0 ; i<5 ; i++)
        topPages[i].proba = 0;
    

    for(int i = 0 ; i<size ; i++){
        int j = 4;
        if(topPages[j].proba < sk[i]){   
            j--;
            while(j >= 0 && topPages[j].proba < sk[i])
            {
                j--;
            }

            for (int k = 4; k > j; k--)
                topPages[k] = topPages[k-1];

            topPages[j+1].page = i;
            topPages[j+1].proba = sk[i];
        }
    }
    for(int i = 0 ; i<5 ; i++){
        printf("Top %d: %d\n", i+1, topPages[i].page);
    }
}

int main() {
    FILE* fp  = fopen("genetic.txt", "r");
    DIGRAPH* g = readDigraph(fp);
    double alpha = 0.9;
    fclose(fp);
    printDigraph(g);

    double * sk = malloc(sizeof(double)*g->n);
    double * skprev = malloc(sizeof(double)*g->n);
    for (int i=0; i<g->n; ++i)
        sk[i] = 1./g->n;
    //printsk(0, sk, g->n);

    double epsilon = pow(10, -10);
    int i = 0;
    int convergence = 0;
    do {
        for (int j=0; j<g->n; ++j)
            skprev[j] = sk[j];

        computeProba(sk, g, alpha);
        //printsk(i, sk, g->n);

        for (int j=0; j<g->n; ++j) {
            convergence = 1;
            if (fabs(sk[j] - skprev[j]) > epsilon) {
                convergence = 0;
                break;
            }
        }

        ++i;
    }
    while (!convergence);
    
    printf("Convergence à k = %d\n", i);
    findTopPages(sk, g->n);
    

    free(sk);
    free(skprev);

    return 0;
}
