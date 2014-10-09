package d5;

import edu.princeton.cs.introcs.StdOut;

public class OptimalHash
{
	public static void main(String[] args)
	{
		char[] keys = { 'S', 'E', 'A', 'R', 'C', 'H', 'X', 'M', 'P', 'L' };
		
		MLoop : for (int M = keys.length; M < 100; M++)
		{
			//StdOut.println("M = " + M);
			aLoop : for (int i = 1; i < 10000; i++)
			{
				boolean[] table = new boolean[M];
				for (int j = 0; j < keys.length; j++)
				{
					int hash = (i * keys[j]) % M;
					//StdOut.println(hash);
					if (table[hash])
					{
						continue aLoop;
					}
					table[hash] = true;
				} // End of keyLoop
				StdOut.println("a: " + i + ", " + "M: " + M);
				break MLoop;
			} // End of primeLoop
		} // end of MLoop
	} // end of main
} // end of OptimalHash
