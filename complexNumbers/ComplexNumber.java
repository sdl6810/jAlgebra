import java.util.*;
import java.lang.Math.*;

public class ComplexNumber
{
	public double real;
	public double imaginary;
	public ComplexNumber(double _real, double _imaginary)
	{
		this.real = _real;
		this.imaginary = _imaginary;
	}

	public void printNumber()
	{
		if (this.imaginary > 0)
		{
			System.out.println(this.real + " + " + Math.abs(this.imaginary) + "i");
		}
		else if (this.imaginary < 0)
		{
			System.out.println(this.real + " - " + Math.abs(this.imaginary) + "i");
		}
		else
		{
			System.out.println("Unknown error.");
		}
	}

	public double getReal()
	{
		return this.real;
	}

	public double getImaginary()
	{
		return this.imaginary;
	}

	public void getConjugate()
	{
		this.imaginary = -1*this.imaginary;
	}

	public double multiplyByConjugate()
	{
		//(a + bi)(a - bi) = a^2 + b^2
		return this.real*this.real + 2*this.imaginary*this.imaginary;
	}

	public static void main(String[] args)
	{
		ComplexNumber c = new ComplexNumber(1, 5);
		c.printNumber();
	}
}
