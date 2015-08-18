/*
	Main header file.
	Contains all pre-processor statements, function prototypes, structures, and constants.

	Author:	Matthew Boyette
	Date:	6/24/2013
*/

// ***** Constants *****

#define _CRT_SECURE_NO_WARNINGS

#if MY_PERSONAL_MAX <= 0
	#define MY_PERSONAL_MAX 10000
#endif

// ***** Includes *****

#include <stdbool.h>
#include <stdarg.h>
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>
#include <math.h>
#include <ctype.h>

// ***** Structures *****

typedef struct
{
	char label;
	bool visited;
} VERTEX;

typedef struct _NODE
{
	char			label;
	struct _NODE*	next;
} NODE;

typedef struct
{
	VERTEX*	vertex;
	NODE*	firstAdj;
} ROW;

typedef struct
{
	size_t	size;
	ROW*	rows;
} ADJLIST;

// ***** Prototypes *****

void bfs(ADJLIST* adjList);
void dfs(ADJLIST* adjList);
ROW* noSuccessors(ADJLIST* adjList);
ADJLIST* parseFile(char* filePath);
void removeDuplicates(char strA[], size_t* size);
void topo(ADJLIST* adjList);