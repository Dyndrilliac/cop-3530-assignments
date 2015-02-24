/*
	Main source file.
	Contains program entry point function.

	Author: Matthew Boyette
	Date:   5/12/2013
*/

#include "main.h"

int main (int argc, char *argv[])
{
	// If there is a command-line argument...
	if (argc > 1)
	{
		int  queryIndex  = 0;
		int  arraySize   = 0;
		int* arrayOfInts = NULL;
		bool validIndex  = true;
		
		// Parse the command-line argument as the path to a file relative to the binary executable.
		arrayOfInts = parseFile(argv[1], &arraySize);
		
		if (arrayOfInts != NULL)
		{
			// Sort the array.
			sort(arrayOfInts, arraySize);
			
			// Ask the user which index of the array they would like to examine until they provide an illegal index.
			do
			{
				printf("\nPlease provide an index: ");
				scanf(" %d", &queryIndex);
				validIndex = ((queryIndex > -1) && (queryIndex < arraySize));
				
				if (validIndex)
				{
					printf("arrayOfInts[%d] = %d\n", queryIndex, arrayOfInts[queryIndex]);
				}
			}
			while (validIndex);
			
			// Free the memory allocated for the array by parseFile.
			free(arrayOfInts);
		}
	}
	
	printf("\nExiting...\n");
	return 0;
}