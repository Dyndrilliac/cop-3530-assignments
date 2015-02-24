/*
 * Title: COP 3530 - Assignment 06
 * Author: Matthew Boyette
 * Date: 6/19/2013
 */

package Assignment06;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import api.util.Mathematics;
import api.util.stdlib.StdIn;
import api.util.stdlib.StdOut;

public class A6
{
	public static class HashTable<T>
	{
		private int			currentSize				= 0;
		private T[]			hashArray				= null;
		private double		loadFactor				= 0.0;
		private int			maximumSize				= 0;
		private boolean		probeIsLinear			= false;
		private Class<T>	storageType				= null;
		private int			totalProbeLenFailure	= 0;
		private int			totalProbeLenSuccess	= 0;

		@SuppressWarnings({"unchecked"})
		public HashTable(final Class<T> type, final double loadFactor, final boolean useLinearProbe)
		{
			this.currentSize = 0;
			this.loadFactor = loadFactor;
			this.probeIsLinear = useLinearProbe;
			this.storageType = type;
			this.setMaximumSize((int)(Math.ceil(A6.FILL_SIZE / this.getLoadFactor())));
			this.hashArray = (T[])Array.newInstance(this.storageType, this.getMaximumSize());
		}

		public int find(final T data)
		{
			if (this.isEmpty())
			{
				return -1;
			}

			int retVal = this.hash(data);
			int quadStep = 0;
			int probeLen = 1;

			if (retVal < 0)
			{
				return retVal;
			}

			while ((this.wasIndexOccupiedPreviously(retVal)) && (data.equals(this.hashArray[retVal]) == false))
			{
				if (this.isProbeLinear())
				{
					retVal++;
				}
				else
				{
					quadStep++;
					retVal = (int)(retVal + Math.pow(quadStep, 2));
				}

				if (retVal >= this.getMaximumSize())
				{
					retVal %= this.getMaximumSize();
				}

				probeLen++;
			}

			if (this.hashArray[retVal] == null)
			{
				retVal = -1;
				this.totalProbeLenFailure += probeLen;
			}
			else
			{
				this.totalProbeLenSuccess += probeLen;
			}

			return retVal;
		}

		public int getCurrentSize()
		{
			return this.currentSize;
		}

		public double getLoadFactor()
		{
			return this.loadFactor;
		}

		public int getMaximumSize()
		{
			return this.maximumSize;
		}

		public int getTotalProbeLenFailure()
		{
			return this.totalProbeLenFailure;
		}

		public int getTotalProbeLenSuccess()
		{
			return this.totalProbeLenSuccess;
		}

		public int hash(final T data)
		{
			int hashVal = 0;

			if (data == null)
			{
				hashVal = -1;
			}
			else
			{
				if (data instanceof String)
				{
					String s = (String)data;

					for (int i = 0; i < s.length(); i++)
					{
						// Take the integer value at the current character index, invert its bits (take one's complement), and store the absolute value as
						// seedVal.
						int seedVal = Math.abs(~((int)(s.charAt(i))));
						// The hash is equal to the remainder of the hashTable size divided by the hash multiplied by 256 and added to seedVal.
						hashVal = ((hashVal * 256) + seedVal) % this.getMaximumSize();
					}
				}
				else
					if (data instanceof Integer)
					{
						String s = data.toString();

						for (int i = 0; i < s.length(); i++)
						{
							// Take the integer value at the current digit index, invert its bits (take one's complement), and store the absolute value as
							// seedVal.
							int seedVal = Math.abs(~(Integer.parseInt(s.substring(i, i + 1))));
							// The hash is equal to the remainder of the hashTable size divided by the hash multiplied by 10 and added to seedVal.
							hashVal = ((hashVal * 10) + seedVal) % this.getMaximumSize();
						}
					}
					else
					{
						hashVal = data.hashCode();
					}
			}

			return hashVal;
		}

		public int insert(final T data)
		{
			if (this.getCurrentSize() >= this.maximumSize)
			{
				return -1;
			}

			int step = 0;
			int hashVal = this.hash(data);

			if (hashVal < 0)
			{
				return hashVal;
			}

			while (this.isIndexOccupied(hashVal))
			{
				if (this.isProbeLinear())
				{
					hashVal++;
				}
				else
				{
					step++;
					hashVal = (int)(hashVal + Math.pow(step, 2));
				}

				if (hashVal >= this.getMaximumSize())
				{
					hashVal %= this.getMaximumSize();
				}
			}

			this.hashArray[hashVal] = data;
			this.currentSize++;
			return hashVal;
		}

		public boolean isEmpty()
		{
			return (this.getCurrentSize() <= 0);
		}

		private boolean isIndexOccupied(final int index)
		{
			boolean retVal = true;

			if ((this.hashArray[index] == null) || (this.hashArray[index].equals(0)) || (this.hashArray[index].equals("")))
			{
				retVal = false;
			}

			return retVal;
		}

		public boolean isProbeLinear()
		{
			return this.probeIsLinear;
		}

		private void setMaximumSize(final int maximumSize)
		{
			this.maximumSize = (int)Mathematics.makePrimeGreater(maximumSize);
		}

		private boolean wasIndexOccupiedPreviously(final int index)
		{
			boolean retVal = true;

			if (this.hashArray[index] == null)
			{
				retVal = false;
			}

			return retVal;
		}
	}

	private static int[]					empirFailureCounter	= new int[A6.LOAD_FACTORS.length * 2];
	private static int[]					empirSuccessCounter	= new int[A6.LOAD_FACTORS.length * 2];
	private static final int				FILL_SIZE			= 10000;
	private static final int				FIND_SIZE			= 100;
	private static List<HashTable<Integer>>	hashTables			= new ArrayList<HashTable<Integer>>();
	private static final double[]			LOAD_FACTORS		= {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
	private static int						maxValueInserted	= 0;
	private static int						maxValueSearched	= 0;
	private static int[]					probeLengthFailure	= new int[A6.LOAD_FACTORS.length * 2];
	private static int[]					probeLengthSuccess	= new int[A6.LOAD_FACTORS.length * 2];

	private static void buildTables()
	{
		for (int i = 0; i < (A6.LOAD_FACTORS.length * 2); i++)
		{
			HashTable<Integer> hashTable;

			if (i <= 8)
			{
				hashTable = new HashTable<Integer>(Integer.class, A6.LOAD_FACTORS[i], true);
			}
			else
			{
				hashTable = new HashTable<Integer>(Integer.class, A6.LOAD_FACTORS[i - 9], false);
			}

			A6.fillTable(hashTable);
			A6.hashTables.add(hashTable);
		}
	}

	private static void doSearches()
	{
		for (int i = 0; i < A6.hashTables.size(); i++)
		{
			HashTable<Integer> hashTable = A6.hashTables.get(i);

			for (int j = 0; j < A6.FIND_SIZE; j++)
			{
				Integer searchVal = Mathematics.getRandomInteger(1, A6.maxValueSearched, true);
				int resultVal = hashTable.find(searchVal);

				if (resultVal >= 0)
				{
					A6.empirSuccessCounter[i]++;
				}
				else
				{
					A6.empirFailureCounter[i]++;
				}
			}

			A6.probeLengthSuccess[i] = hashTable.getTotalProbeLenSuccess();
			A6.probeLengthFailure[i] = hashTable.getTotalProbeLenFailure();
		}
	}

	private static void fillTable(final HashTable<Integer> hashTable)
	{
		for (int i = 0; i < A6.FILL_SIZE; i++)
		{
			hashTable.insert(Mathematics.getRandomInteger(1, A6.maxValueInserted, true));
		}
	}

	public static void main(final String[] args)
	{
		StdOut.println("");

		A6.promptInput();

		A6.buildTables();

		StdOut.printf("%d hash tables have been generated and filled with %d random integers between %d and %d (inclusively).\n" +
			"The program will now go through and search each table for %d random integers between %d and %d (inclusively).\n" +
			"It keeps track of the probe lengths needed to either find an integer or determine that it is not in the table.\n\n",
			A6.hashTables.size(), A6.FILL_SIZE, 1, A6.maxValueInserted, A6.FIND_SIZE, 1, A6.maxValueSearched);

		A6.doSearches();

		A6.printResults();

		System.exit(0);
	}

	private static void printResults()
	{
		A6.printResultsTable(true);
		StdOut.println("");
		A6.printResultsTable(false);
		StdOut.println("");
	}

	private static void printResultsTable(final boolean isProbeLinear)
	{
		final int rows = A6.LOAD_FACTORS.length;

		int index;

		StdOut.println("-----------------------------------------------------------------------------------");
		StdOut.printf(
			"%-8.8s %-12.12s %-12.12s %-12.12s %-12.12s %-12.12s %-12.12s\n",
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
				theoreticalSuccess = ((-1.0 * (Mathematics.logarithm(2.0, (1.0 - L)))) / L);
				theoreticalFailure = (1.0 / (1.0 - L));
			}

			if (A6.empirSuccessCounter[index] != 0)
			{
				empiricalSuccess = (((double)A6.probeLengthSuccess[index]) / ((double)A6.empirSuccessCounter[index]));
			}

			if (A6.empirFailureCounter[index] != 0)
			{
				empiricalFailure = (((double)A6.probeLengthFailure[index]) / ((double)A6.empirFailureCounter[index]));
			}

			double[] output =
			{
				A6.LOAD_FACTORS[i],
				empiricalSuccess,
				A6.empirSuccessCounter[index],
				theoreticalSuccess,
				empiricalFailure,
				A6.empirFailureCounter[index],
				theoreticalFailure};
			StdOut.printf(
				"%-8.2f %-12.4f %-12.0f %-12.4f %-12.4f %-12.0f %-12.4f\n",
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

	private static void promptInput()
	{
		String input = null;

		do
		{
			StdOut.print("Provide two integers on one line separated by a space:\n\n");
			input = StdIn.readLine().trim();
			StdOut.println("");
		}
		while (A6.validateInput(input) == false);

		String[] sArgs = input.split(" ");
		int[] iArgs = {Integer.parseInt(sArgs[0]), Integer.parseInt(sArgs[1])};

		A6.maxValueInserted = iArgs[0];
		A6.maxValueSearched = iArgs[1];
	}

	private static boolean validateInput(final String s)
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