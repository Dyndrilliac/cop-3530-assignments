/*
	Swap source file.
	Contains swap function.

	Author:	Matthew Boyette
	Date:	5/13/2013
*/

#include "main.h"

void swap(int* arrayPointer, size_t indexA, size_t indexB)
{
	// Create a temporary buffer and store the value from arrayPointer[indexA].
	int temp = arrayPointer[indexA];
	// Store the value from arrayPointer[indexB] at arrayPointer[indexA].
	arrayPointer[indexA] = arrayPointer[indexB];
	// Store the value from the temporary buffer at arrayPointer[indexB].
	arrayPointer[indexB] = temp;
}