/*
	NoSuccessors source file.
	Contains function to return a pointer to the first row encountered through a sequential search that represents a vertex that is not the starting point for any edge.

	Author:	Matthew Boyette
	Date:	7/2/2013
*/

#include "main.h"

ROW* noSuccessors(ADJLIST* adjList)
{
	ROW* row = NULL;
	
	if (adjList != NULL)
	{
		for (size_t i = 0; i < adjList->size; i++)
		{
			if (adjList->rows[i].firstAdj == NULL)
			{
				return &adjList->rows[i];
			}
		}
	}

	return row;
}