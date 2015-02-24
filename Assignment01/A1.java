/*
 * Title: COP 3530 - Assignment 01
 * Author: Matthew Boyette
 * Date: 5/11/2013
 */

package Assignment01;

import api.util.stdlib.StdOut;

public class A1
{
	protected static class HighArray
	{
		protected int[]	a;
		protected int	size;

		public HighArray(final int max)
		{
			this.a = new int[max];
			this.size = 0;
		}

		public void delete(final int value)
		{
			int x = this.find(value);

			if (x >= 0)
			{
				for (int k = x; k < this.getSize(); k++)
				{
					this.a[k] = this.a[k + 1];
				}
				this.size--;
			}
		}

		public void display()
		{
			for (int j = 0; j < this.getSize(); j++)
			{
				StdOut.print(this.a[j] + " ");
			}

			StdOut.println("");
		}

		public int find(final int value)
		{
			for (int j = 0; j < this.getSize(); j++)
			{
				if (this.a[j] == value)
				{
					return j;
				}
			}

			return -1;
		}

		public int getMax()
		{
			int result = -1;

			for (int i = 0; i < this.getSize(); i++)
			{
				if (this.a[i] > result)
				{
					result = this.a[i];
				}
			}

			return result;
		}

		public final int getSize()
		{
			return this.size;
		}

		public void insert(final int value)
		{
			this.a[this.getSize()] = value;
			this.size++;
		}
	}

	public static void main(final String[] args)
	{
		final int maxSize = 100;
		HighArray arr = new HighArray(maxSize);

		arr.insert(77);
		arr.insert(99);
		arr.insert(44);
		arr.insert(55);
		arr.insert(22);
		arr.insert(88);
		arr.insert(11);
		arr.insert(0);
		arr.insert(66);
		arr.insert(33);
		arr.display();

		int searchKey = 35;

		if (arr.find(searchKey) >= 0)
		{
			StdOut.println("Found " + searchKey);
		}
		else
		{
			StdOut.println("Can't find " + searchKey);
		}

		arr.delete(0);
		arr.delete(55);
		arr.delete(99);
		arr.display();

		StdOut.println("Maximum: " + arr.getMax());
	}
}