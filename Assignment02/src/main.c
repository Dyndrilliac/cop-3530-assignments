/*
	Main source file.
	Contains program entry point function.

	Author:	Matthew Boyette
	Date:	5/12/2013
*/

#include "main.h"

int main (int argc, char *argv[])
{
	// If there is a command-line argument...
	if (argc > 1)
	{
		size_t	queryIndex	= 0;
		size_t	arraySize	= 0;
		int*	arrayOfInts	= NULL;
		bool	validIndex	= true;
		
		// Parse the command-line argument as the path to a file relative to the binary executable.
		arrayOfInts = parseFile(argv[1], &arraySize);
		
		if (arrayOfInts != NULL)
		{
			// Sort the array.
			sort(arrayOfInts, arraySize);
			
			// Ask the user which index of the array they would like to examine until they provide an illegal index.
			do
			{
				fprintf(stdout, "\nPlease provide an index: ");
				fscanf(stdin, " %d", &queryIndex);
				//validIndex = ((queryIndex > -1) && (queryIndex < arraySize));
				validIndex = (queryIndex < arraySize);
				
				if (validIndex)
				{
					fprintf(stdout, "arrayOfInts[%d] = %d\n", queryIndex, arrayOfInts[queryIndex]);
				}
				else
				{
					fprintf(stdout, "Error: %d is an invalid index. (arraySize = %d)\n", queryIndex, arraySize);
				}
			}
			while (validIndex);
			
			// Free the memory allocated for the array by parseFile.
			free(arrayOfInts);
		}
	}
	else
	{
		// Report error if no command-line parameters were given.
		fprintf(stderr, "\nError: provide the path to a plain-text file via command-line parameter!\nExample usage: %s <file>\n", argv[0]);
		exit(1);
	}
	
	fprintf(stdout, "\nExiting...\n");
	return 0;
}