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
}