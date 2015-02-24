/*
	Main source file.
	Contains program entry point function.

	Author: Matthew Boyette
	Date:   6/24/2013
*/

#include "main.h"

int main (int argc, char *argv[])
{
	size_t i;

	// If there is a command-line argument...
	if (argc > 1)
	{
		// Read the file, build the adjacency list, and get a pointer to it.
		ADJLIST* adjList = parseFile(argv[1]);

		if (adjList != NULL)
		{
			// Run the main routines on the adjacency list.
			dfs(adjList);
			bfs(adjList);
			topo(adjList);

			// Free up the memory allocated to the adjacency list.
			for (i = 0; i < adjList->size; i++)
			{
				free(adjList->rows[i].vertex);
				free(adjList->rows[i].firstAdj);
			}

			free(adjList->rows);
			free(adjList);
		}
	}
	else
	{
		// Report error if no command-line parameters were given.
		fprintf(stderr, "\nError: provide the path to a plain-text file via command-line parameter!\nExample usage: %s <file>\n", argv[0]);
		exit(1);
	}
	
	return 0;
}