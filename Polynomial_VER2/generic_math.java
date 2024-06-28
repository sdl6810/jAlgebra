import java.util.*;

public class generic_math
{
	public generic_math()
	{

	}

	public static double randbetween(int min, int max)
	{
		Random r = new Random();
		double result = (double)r.nextInt(max-min)+min;
		return result;
	}

	public static double roundTo(double value, int digits)
	{
		double scale = Math.pow(10,digits);
		return Math.round(value*scale)/scale;
	}

	public static double gcd(double a, double b)
	{
		while (b != 0)
		{
			double temp = b;
			b = a%b;
			a = temp;
		}
		return a;
	}

	public static ArrayList<Double> factor(double value)
	{
		ArrayList<Double> factors = new ArrayList<Double>();

		factors.add(1.0);
		int i = 2;
		while (i <= value)
		{
			if (value%i == 0)
				factors.add((double)i);

			i++;
		}

		return factors;
	}

	public static boolean thisArrayContainsElement(String[] arr, String x)
	{
		boolean insideArray = false;
		for (int i = 0; i < arr.length; i++)
		{
			if (x.equals(arr[i]))
			{
				insideArray = true;
				break;
			}
		}
		return insideArray;
	}

	public static void main(String[] args)
	{
		double number = generic_math.randbetween(1,20);
		System.out.println(number);
		String[] someNumbers = {"1.0","2.0","3.0","4.0","10.0","15.0","12.0","9.0","17.0"};

		boolean insideNumbers = generic_math.thisArrayContainsElement(someNumbers,Double.toString(number));
		System.out.println(insideNumbers);
	}
}