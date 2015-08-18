/*
	ParseFile source file.
	Contains function to parse a file containing directional edges connected by two alphanumeric vertices.

	Author:	Matthew Boyette
	Date:	6/24/2013
*/

#include "main.h"

ADJLIST* parseFile(char* filePath)
{
	// Open the file.
	FILE* inputFile = fopen(filePath, "r");

	// Set up some variables to store stuff.
	ADJLIST* adjList   = NULL;
	char*    preArray  = NULL;
	char*    postArray = NULL;
	int      character = 0;
	size_t   preSize   = 0;
	size_t   postSize  = 0;

	if (inputFile != NULL)
	{
		// Allocate memory to cache the file's data only if we successfully opened the file.
		preArray  = (char*)calloc(MY_PERSONAL_MAX, sizeof(char));
		postArray = (char*)calloc(MY_PERSONAL_MAX, sizeof(char));

		// Return a NULL pointer if we fail to allocate cache space.
		if ((preArray == NULL) || (postArray == NULL))
		{
			return NULL;
		}

		// Keep checking characters until we get to the end of the file.
		while ((character = fgetc(inputFile)) != EOF)
		{
			// Is the character alphanumeric?
			if (isalnum(character))
			{
				// Yes! Cache it, count it, and keep going.
				preArray[preSize] = ((char)character);
				preSize++;
				
				// Bounds check.
				if (preSize >= (MY_PERSONAL_MAX - 1))
				{
					break;
				}
			}
		}
		
		// Set the string's null-terminating character.
		preArray[preSize] = ((char)0);
		
		// Close the file.
		fclose(inputFile);
	}
	
	// Report error if the input file contained an odd number of vertices. We expect a list of edges, and each edge must have 2 vertices.
	if ((preSize % 2) != 0)
	{
		fprintf(stderr, "\nError: input file contained an odd number of vertices.\n");
		exit(2);
	}
	
	// Copy the list of edges obtained from the file.
	strcpy(postArray, preArray);

	// Sort the copy and remove the duplicate characters to create a list of only the unique vertices.
	postSize = preSize;
	removeDuplicates(&postArray[0], &postSize);
	
	// Allocate space for the adjacency list.
	adjList = (ADJLIST*)malloc(sizeof(ADJLIST));

	// Begin building the list if initial memory allocation was successful.
	if (adjList != NULL)
	{
		adjList->size = postSize;
		adjList->rows = (ROW*)calloc(adjList->size, sizeof(ROW));

		if (adjList->rows != NULL)
		{
			fprintf(stdout, "\nAdjacency List:\n\n");
			for (size_t i = 0; i < adjList->size; i++)
			{
				NODE* currAdj;
				ROW*  currRow = &adjList->rows[i];
				currRow->vertex = (VERTEX*)malloc(sizeof(VERTEX));
				currRow->vertex->label   = postArray[i];
				currRow->vertex->visited = false;
				fprintf(stdout, "\tRow[%d]: (%c) -> {", i, currRow->vertex->label);

				currAdj = currRow->firstAdj;
				for (size_t j = 0; j < preSize; j += 2)
				{
					if (currRow->vertex->label == preArray[j])
					{
						currAdj = (NODE*)malloc(sizeof(NODE));
						currAdj->label = preArray[j+1];
						currAdj->next  = NULL;
						fprintf(stdout, "(%c)", currAdj->label);
						currAdj = currAdj->next;
					}
				}

				fprintf(stdout, "}\n");
			}
		}
		else
		{
			free(adjList);
			adjList = NULL;
		}
	}

	// Free allocated memory we no longer need.
	free(preArray);
	free(postArray);

	// Return the adjacency list.
	return adjList;
}