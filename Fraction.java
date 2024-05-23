import java.io.*;
import java.util.*;
import java.lang.Math.*;

public class Fraction
{
	int _num = 0;
	int _den = 0;
	public Fraction(int numerator, int denominator)
	{
		this._num = numerator;
		this._den = denominator;
	}

	public String displayFraction()
	{
		return Integer.toString(this._num) + "/" + Integer.toString(this._den);
	}

	private static int findLCM(int number1, int number2) 
	{
	    if (number1 == 0 || number2 == 0) 
	    {
	        return 0;
	    }
	    int absNumber1 = Math.abs(number1);
	    int absNumber2 = Math.abs(number2);
	    int absHigherNumber = Math.max(absNumber1, absNumber2);
	    int absLowerNumber = Math.min(absNumber1, absNumber2);
	    int lcm = absHigherNumber;
	    while (lcm % absLowerNumber != 0) 
	    {
	        lcm += absHigherNumber;
	    }
	    return lcm;
	}

	//could be used for both adding and subtracting
	//second value should be negative to do this
	public Fraction combine(Fraction a, Fraction b)
	{
		int lcm = 0;
		int numeratorSum = 0;
		if (a._den == b._den)
		{
			numeratorSum = a._num + b._num;
		}
		else if (a._den != b._den)
		{
			lcm = this.findLCM(a._den,b._den);
			a._den = lcm;
			b._den = lcm;
			int factor_a = lcm/a._den;
			int factor_b = lcm/b._den;
			a._num = factor_a*a._num;
			b._num = factor_b*b._num;
			numeratorSum = a._num + b._num;
		}
		Fraction result = new Fraction(numeratorSum,lcm);
		return result;
	}

	public Fraction multiply(Fraction a, Fraction b)
	{
		int numerator = a._num*b._num;
		int denominator = a._den*b._den;
		Fraction product = new Fraction(numerator,denominator);
		return product;
	}

	private void getReciprocal()
	{
		int tempNum = this._num;
		int tempDen = this._den;
		this._num = tempDen;
		this._den = tempNum;
	}

	public Fraction divide(Fraction a, Fraction b)
	{
		b.getReciprocal();
		Fraction result = multiply(a,b);
		return result;
	}

	private double roundTo(double value, int digits)
	{
		double scale = Math.pow(10,digits);
		return Math.round(value*scale)/scale;
	}

	public double toDecimal(Fraction f)
	{
		return (double)f._num/(double)f._den;
	}

	public static Fraction toRatio(double value)
	{		
		double decimal = value - (double)((int)value);
		String decimalString = Double.toString(decimal);
		decimalString = decimalString.substring(2,decimalString.length());
		int digitsAfterDecimal = decimalString.length();
		double newNumerator = 0.0;
		double newDenominator = 0.0;
		if (decimalString.length() > 6)
		{
			decimalString = decimalString.substring(0,6);
		}
		int j = decimalString.length()-1;
		for (int i = 0; i < decimalString.length(); i++)
		{
			newNumerator = newNumerator + Double.parseDouble(Character.toString(decimalString.charAt(i)))*Math.pow(10,j);
			j--;	
		}
		newDenominator = Math.pow(10,digitsAfterDecimal);
		Fraction newFraction = new Fraction((int)newNumerator, (int)newDenominator);
		return newFraction;
	}

	public String toMixedNumber()
	{
		int differenceOverWhole = this._num - this._den;
		int amountOfWholes = (int)this._num/this._den;

		Fraction leftoverPart = new Fraction(differenceOverWhole,this._den);
		return Integer.toString(amountOfWholes)+" "+leftoverPart.displayFraction();
	}

	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		int frac1_num, frac1_den, frac2_num, frac2_den;

		System.out.println("Enter the info for the first fraction");
		frac1_num = keyboard.nextInt();
		frac1_den = keyboard.nextInt();

		System.out.println("Enter the info for the second fraction");
		frac2_num = keyboard.nextInt();
		frac2_den = keyboard.nextInt();

		Fraction f = new Fraction(frac1_num, frac1_den);
		Fraction g = new Fraction(frac2_num, frac2_den);
		f.displayFraction();
		g.displayFraction();
		Fraction s = f.divide(f,g);
		s.displayFraction();

		Fraction test = Fraction.toRatio(0.27);
		test.displayFraction();

		String frac = test.toMixedNumber();
		System.out.println(frac);
	}
}