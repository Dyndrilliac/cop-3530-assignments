/*
 * Title: COP 3530 - Assignment 06
 * Author: Matthew Boyette
 * Date: 6/19/2013
 */

package Assignment06;

import java.util.ArrayList;
import java.util.List;

import api.util.Mathematics;
import api.util.datastructures.HashTable;
import api.util.stdlib.StdIn;
import api.util.stdlib.StdOut;

public class A6
{
	/*
	 * You can play with these constants to test the program in more types of conditions!
	 */
	// Two tables will be constructed for each load factor, one using Linear probing and one using Quadratic probing.
	public static final int			FILL_SIZE		= 10000; // How many items to place randomly in the hash table.
	public static final int			FIND_SIZE		= 100;   // How many times to search for a random integer.
	public static final double[]	LOAD_FACTORS	= {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9}; // Sample load factors.
																										
	public static void main(final String[] args)
	{
		A6 hashTableAnalyzer = new A6();

		hashTableAnalyzer.promptInput();
		hashTableAnalyzer.buildTables();
		hashTableAnalyzer.printPreamble();
		hashTableAnalyzer.doSearches();
		hashTableAnalyzer.printResults();

		System.exit(0);
	}

	// Empirical Counters
	private final int[]	empirFailureCounter	= new int[A6.LOAD_FACTORS.length * 2];
	private final int[]	empirSuccessCounter	= new int[A6.LOAD_FACTORS.length * 2];
	
	// Table list
	private final List<HashTable<Integer>> hashTables = new ArrayList<HashTable<Integer>>();

	// Limits - These are set by user input!
	private int	maxValueInserted = 0;
	private int	maxValueSearched = 0;

	// Probe Length Counters
	private final int[] probeLengthFailure = new int[A6.LOAD_FACTORS.length * 2];
	private final int[] probeLengthSuccess = new int[A6.LOAD_FACTORS.length * 2];
	
	public void buildTables()
	{
		for (int i = 0; i < (A6.LOAD_FACTORS.length * 2); i++)
		{
			HashTable<Integer> hashTable;
			
			if (i <= 8)
			{
				hashTable = new HashTable<Integer>(Integer.class, A6.LOAD_FACTORS[i], true, A6.FILL_SIZE);
			}
			else
			{
				hashTable = new HashTable<Integer>(Integer.class, A6.LOAD_FACTORS[i - 9], false, A6.FILL_SIZE);
			}
			
			this.fillTable(hashTable);
			this.hashTables.add(hashTable);
		}
	}
	
	public void doSearches()
	{
		for (int i = 0; i < this.hashTables.size(); i++)
		{
			HashTable<Integer> hashTable = this.hashTables.get(i);
			
			for (int j = 0; j < A6.FIND_SIZE; j++)
			{
				Integer searchVal = Mathematics.getRandomInteger(1, this.maxValueSearched, true);
				int resultVal = hashTable.find(searchVal);
				
				if (resultVal >= 0)
				{
					this.empirSuccessCounter[i]++;
				}
				else
				{
					this.empirFailureCounter[i]++;
				}
			}
			
			this.probeLengthSuccess[i] = hashTable.getTotalProbeLenSuccess();
			this.probeLengthFailure[i] = hashTable.getTotalProbeLenFailure();
		}
	}
	
	private void fillTable(final HashTable<Integer> hashTable)
	{
		for (int i = 0; i < A6.FILL_SIZE; i++)
		{
			hashTable.insert(Mathematics.getRandomInteger(1, this.maxValueInserted, true));
		}
	}

	public void printPreamble()
	{
		StdOut.printf("%d hash tables have been generated and filled with %d random integers between %d and %d (inclusively).\n" +
			"The program will now go through and search each table for %d random integers between %d and %d (inclusively).\n" +
			"It keeps track of the probe lengths needed to either find an integer or determine that it is not in the table.\n\n",
			this.hashTables.size(), A6.FILL_SIZE, 1, this.maxValueInserted, A6.FIND_SIZE, 1, this.maxValueSearched);
	}
	
	public void printResults()
	{
		this.printResultsTable(true);
		StdOut.println("");
		this.printResultsTable(false);
		StdOut.println("");
	}
	
	private void printResultsTable(final boolean isProbeLinear)
	{
		final int rows = A6.LOAD_FACTORS.length;
		
		int index;
		
		StdOut.println("-----------------------------------------------------------------------------------");
		StdOut.printf("%-8.8s %-12.12s %-12.12s %-12.12s %-12.12s %-12.12s %-12.12s\n",
			"Load",
			"E-Success",
			"Denominator",
			"T-Success",
			"E-Failure",
			"Denominator",
			"T-Failure");
		StdOut.println("-----------------------------------------------------------------------------------");
		
		for (int i = 0; i < rows; i++)
		{
			double L = A6.LOAD_FACTORS[i];
			double empiricalSuccess = 0.0;
			double empiricalFailure = 0.0;
			double theoreticalSuccess = 0.0;
			double theoreticalFailure = 0.0;
			
			if (isProbeLinear)
			{
				index = i;
				theoreticalSuccess = (1.0 / 2.0) * (1.0 + (1.0 / (1.0 - L)));
				theoreticalFailure = (1.0 / 2.0) * (1.0 + (1.0 / Math.pow((1.0 - L), 2.0)));
			}
			else
			{
				index = (i + 9);
				theoreticalSuccess = ((-1.0 * (Mathematics.logarithm((1.0 - L), 2.0))) / L);
				theoreticalFailure = (1.0 / (1.0 - L));
			}
			
			if (this.empirSuccessCounter[index] != 0)
			{
				empiricalSuccess = (((double)this.probeLengthSuccess[index]) /
					((double)this.empirSuccessCounter[index]));
			}
			
			if (this.empirFailureCounter[index] != 0)
			{
				empiricalFailure = (((double)this.probeLengthFailure[index]) /
					((double)this.empirFailureCounter[index]));
			}
			
			double[] output =
			{
				A6.LOAD_FACTORS[i],
				empiricalSuccess,
				this.empirSuccessCounter[index],
				theoreticalSuccess,
				empiricalFailure,
				this.empirFailureCounter[index],
				theoreticalFailure
			};

			StdOut.printf("%-8.2f %-12.4f %-12.0f %-12.4f %-12.4f %-12.0f %-12.4f\n",
				output[0],
				output[1],
				output[2],
				output[3],
				output[4],
				output[5],
				output[6]);
		}
		
		StdOut.println("-----------------------------------------------------------------------------------");
	}
	
	public void promptInput()
	{
		String input = null;
		StdOut.println("");

		do
		{
			StdOut.print("Provide two integers on one line separated by a space:\n\n");
			input = StdIn.readLine().trim();
			StdOut.println("");
		}
		while (this.validateInput(input) == false);
		
		String[] sArgs = input.split(" ");
		int[] iArgs = {Integer.parseInt(sArgs[0]), Integer.parseInt(sArgs[1])};
		
		this.maxValueInserted = iArgs[0];
		this.maxValueSearched = iArgs[1];
	}
	
	private boolean validateInput(final String s)
	{
		if (s == null)
		{
			return false;
		}
		else
		{
			return s.matches("[0-9]+ [0-9]+");
		}
	}
}