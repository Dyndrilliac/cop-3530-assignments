/*
	Main header file.
	Contains all prepocessor statements, function prototypes, structures, and constants.

	Author: Matthew Boyette
	Date:   5/12/2013
*/

// ***** Includes *****

#include <stdbool.h>
#include <stdarg.h>
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>
#include <math.h>

// ***** Prototypes *****

int* parseFile(char* filePath, int* sizePointer);
void sort(int* arrayPointer, int size);
void swap(int* arrayPointer, int indexA, int indexB);