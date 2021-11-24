package soe;

/*
 * SieveOfErastosthenes - contains a single method that receives
 *    an integer as input and finds all prime numbers less than or
 *    equal to the input. Once all prime numbers are identified
 *    they are output to stdout.
 */
public class SieveOfErastosthenes 
{
	public void findPrimes(final int limit)
	{
		System.out.println("Prime numbers <= " + limit + ":");

		// Create array of booleans and set all values to 'true'
		boolean vals[] = new boolean[limit+1];
		for (int i = 2; i <= limit; i++)
			vals[i] = true;
		
		// Loop through array from 2 to the sqrt of the specified limit
		for (int i = 2; (i*i) <= limit; i++)
		{
			if (vals[i] == true)
			{
				// Find indices with multiples and set value at index to 'false'
				for (int j = (i*i); j <= limit; j += i)
				{
					vals[j] = false;
				}
			}
		}
		
		// Output prime numbers in rows of 10
		int count = 0;
		for (int i = 2; i <= limit; i++)
		{
			if (count == 10)
			{
				System.out.println();
				count = 0;
			}
			
			if (vals[i])
			{
				System.out.print(String.format("%4d", i) + " ");
				count++;
			}
		}

		System.out.println();
	}	
}
