/*
	Main header file.
	Contains all prepocessor statements, function prototypes, structures, and constants.

	Author:	Matthew Boyette
	Date:	5/12/2013
*/

// ***** Constants *****

#define _CRT_SECURE_NO_WARNINGS

// ***** Includes *****

#include <stdbool.h>
#include <stdarg.h>
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>
#include <math.h>
#include <ctype.h>

// ***** Prototypes *****

int* parseFile(char* filePath, size_t* sizePointer);
void sort(int* arrayPointer, size_t size);
void swap(int* arrayPointer, size_t indexA, size_t indexB);