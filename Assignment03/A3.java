/*
 * Title: COP 3530 - Assignment 03
 * Author: Matthew Boyette
 * Date: 5/24/2013
 */

package Assignment03;

import javax.swing.JOptionPane;
import api.util.Support;
import api.util.datastructures.DoubleLinkedList;
import api.util.datastructures.DoubleLinkedListIterator;
import edu.princeton.cs.algs4.StdOut;

public class A3
{
    public static int[] getParameters()
    {
        int[] retVal = { 0, 0, 0 };
        String parameterString = Support.getInputString(null, "Please enter the necessary parameters:", "Parameter Input");
        String[] parameters = parameterString.split(" ");

        if ( parameters.length != 3 )
        {
            StdOut.println("Incorrect number of parameters.\n");
            System.exit(1);
        }

        if ( Support.isStringParsedAsInteger(parameters[0]) )
        {
            retVal[0] = Integer.parseInt(parameters[0]);
        }
        else
        {
            StdOut.println("First parameter is not an integer.\n");
            System.exit(1);
        }

        if ( Support.isStringParsedAsInteger(parameters[1]) )
        {
            retVal[1] = Integer.parseInt(parameters[1]);
        }
        else
        {
            StdOut.println("Second parameter is not an integer.\n");
            System.exit(1);
        }

        if ( Support.isStringParsedAsInteger(parameters[2]) )
        {
            retVal[2] = Integer.parseInt(parameters[2]);
        }
        else
        {
            StdOut.println("Third parameter is not an integer.\n");
            System.exit(1);
        }

        return retVal;
    }

    public static void josephusProblem()
    {
        int[] parameters = A3.getParameters();

        int total = parameters[0];
        int start = parameters[1];
        int step = parameters[2];

        StdOut.println("Total: " + total + "\nStarting point: " + start + "\nStepping: " + step + "\n");

        DoubleLinkedList<Integer> myList = new DoubleLinkedList<Integer>();

        for ( int i = 1; i <= total; i++ )
        {
            myList.insertTail(i);
        }

        StdOut.println("List: \n" + myList.toString());
        DoubleLinkedListIterator<Integer> iterator = myList.getIterator();
        iterator.resetHead();

        for ( int i = 1; i < start; i++ )
        {
            iterator.nextNode();
        }

        StdOut.println("Starting point: " + iterator.getCurrent().toString() + "\n");

        while ( myList.getSize() > 1 )
        {
            for ( int i = 1; i <= step; i++ )
            {
                StdOut.println("Skipped: " + iterator.getCurrent().toString());
                iterator.nextNode();
            }

            StdOut.println("Deleted: " + iterator.getCurrent().toString());
            iterator.deleteCurrent();
            StdOut.println("\nList: \n" + myList.toString());
        }

        JOptionPane.showMessageDialog(null, "Final integer: " + iterator.getCurrent().toString(), "Result", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(final String[] args)
    {
        A3.josephusProblem();
    }
}
