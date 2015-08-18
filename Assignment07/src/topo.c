/*
	Topo source file.
	Contains topological sort function.

	Author:	Matthew Boyette
	Date:	7/1/2013
*/

#include "main.h"

void topo(ADJLIST* adjList)
{
	char curr = '\0';
	ROW* row  = NULL;

	fprintf(stdout, "\nTopological sort:\n\n");

	do
	{
		row = noSuccessors(adjList);
		curr = row->vertex->label;
		fprintf(stdout, "%c ", curr);

		// TODO
	}
	while (row != NULL);

	fprintf(stdout, "\n");
}