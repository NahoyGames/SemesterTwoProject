package Util.Arrays;

public class Arrays
{
	public static<T> boolean contains(T[] arr, T value)
	{
		for (T v : arr)
		{
			if (v.equals(value))
			{
				return true;
			}
		}

		return false;
	}


	public static boolean contains(char[] arr, char value)
	{
		for (char v : arr)
		{
			if (v == value)
			{
				return true;
			}
		}

		return false;
	}


	public static boolean contains(int[] arr, int value)
	{
		for (int v : arr)
		{
			if (v == value)
			{
				return true;
			}
		}

		return false;
	}
}
