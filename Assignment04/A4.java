/*
 * Title: COP 3530 - Assignment 04
 * Author: Matthew Boyette
 * Date: 5/27/2013
 */

package Assignment04;

import api.util.Support;
import api.util.mathematics.KnapsackSolver;

public class A4
{
	public static void main(final String[] args)
	{
		KnapsackSolver knapsackSolver = null;
		String inputString = null;
		boolean continueFlag = false;
		
		do
		{
			inputString = Support.getInputString(null,
				"*Note: You may enter \"stop\" (not case sensitive) to exit the program.\n" +
					"\nPlease enter the necessary parameters:",
				"Parameter Input");
			continueFlag = ((inputString != null) && (inputString.equalsIgnoreCase("stop") == false));
			
			if (continueFlag)
			{
				String[] parameters = inputString.split(" ");
				int[] weights = new int[parameters.length - 1];
				int capacity = 0;
				
				if (Support.isStringParsedAsInteger(parameters[0]))
				{
					capacity = Integer.parseInt(parameters[0]);
				}
				else
				{
					Support.displayException(null,
						new Exception("Error Parsing Input: capacity parameter is not an integer!"),
						true);
				}
				
				for (int i = 0, j = (weights.length - 1); i < weights.length; i++, j--)
				{
					if (Support.isStringParsedAsInteger(parameters[j + 1]))
					{
						weights[(i)] = Integer.parseInt(parameters[j + 1]);
					}
					else
					{
						Support.displayException(null,
							new Exception("Error Parsing Input: weight[" + (i) + "] parameter is not an integer."),
							true);
					}
				}
				
				knapsackSolver = new KnapsackSolver(capacity, weights);
				knapsackSolver.displaySolutionSets();
			}
		}
		while (continueFlag);
		
		System.exit(0);
	}
}