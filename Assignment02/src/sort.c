/*
	Sort source file.
	Contains sort function.

	Author:	Matthew Boyette
	Date:	5/14/2013
*/

#include "main.h"

void sort(int* arrayPointer, size_t size)
{
	bool sorted = false; // Necessary initial condition.
	
	// Continue to execute the algorithm until the array is completely sorted.
	while (!sorted)
	{
		// Set sorted to true so that if neither iteration through the array produce any swaps the loop will end.
		sorted = true;
		
		// Sort the even indices.
		for (size_t i = 0; i < (size-1); i += 2)
		{
			if(arrayPointer[i] > arrayPointer[i+1])
			{
				swap(arrayPointer, i, i+1);
				sorted = false;
			}
		}
		
		// Sort the odd indices.
		for (size_t j = 1; j < (size - 1); j += 2)
		{
			if(arrayPointer[j] > arrayPointer[j+1])
			{
				swap(arrayPointer, j, j+1);
				sorted = false;
			}
		}
	}
}