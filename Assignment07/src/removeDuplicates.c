/*
	RemoveDuplicate source file.
	Contains function to BubbleSort a string and remove duplicate characters.

	Author:	Matthew Boyette
	Date:	7/1/2013
*/

#include "main.h"

void removeDuplicates(char strA[], size_t* size)
{
	char   temp;
	int    map = 0;

	// This algorithm simulates the behavior of a hash map to replace duplicate characters in a string with asterisks. 
	for (size_t i = 0; i < *size; i++)
	{
		if ((map & (1 << (strA[i] - ' '))) > 0) // Duplicate detected.
		{
			strA[i] = '*';
		}
		else // Set the value of the bits for unique characters to '1' in the map.
		{
			map |= 1 << (strA[i] - ' ');
		}
	}

	// Sort the array in descending order so the asterisks are at the end.
	for (size_t i = (*size - 1); i > 0; i--)
	{
		for (size_t j = 1; j <= i; j++)
		{
            if (strA[j-1] < strA[j])
			{
                temp = strA[j-1];
                strA[j-1] = strA[j];
                strA[j] = temp;
            }
        }   
    }

	// Count the length of the new string and replace the asterisks with null-terminating characters.
	for (size_t i = 0, map = 0; i < *size; i++)
	{
		if (strA[i] != '*')
		{
			map++;
		}
		else
		{
			strA[i] = '\0';
		}
	}

	*size = map;

	// Sort the array in ascending order.
	for (size_t i = (*size - 1); i > 0; i--)
	{
		for (size_t j = 1; j <= i; j++)
		{
            if (strA[j-1] > strA[j])
			{
                temp = strA[j-1];
                strA[j-1] = strA[j];
                strA[j] = temp;
            }
        }   
    }
}