import java.util.*;
import java.lang.Math.*;



public class C_arithmetic
{
	//can be used for adding or subtracting numbers
	public static ComplexNumber combineLikeTerms(ComplexNumber a, ComplexNumber b)
	{
		double reals = a.real + b.real;
		double imags = a.imaginary + b.imaginary;
		ComplexNumber sum = new ComplexNumber(reals, imags);
		return sum;
	}

	public static ComplexNumber multiply(ComplexNumber m, ComplexNumber n)
	{
		//product of two complex numbers is
		//	m        n
		//(a + bi)(e + fi)
		// = ae + afi + bie + bfi^2
		// = ae + afi + bei - bf
		//group into reals and imaginaries
		//(ae - bf) + (af + be)i

		double realProduct = m.real*n.real - m.imaginary*n.imaginary;
		double imaginaryProduct = m.real*n.imaginary + m.imaginary*n.real;
		ComplexNumber cProduct = new ComplexNumber(realProduct, imaginaryProduct);
		return cProduct;
	}

	public static ComplexNumber divide(ComplexNumber p, ComplexNumber r)
	{
		//when dividing two complex numbers, we have to mulitiply BOTH top and bottom by the conjugate of the denominator
		//the second parameter is designated as the denominator
		double realNumerator = p.real*r.real + p.imaginary*r.imaginary;
		double imagNumerator = p.imaginary*r.real - p.real*r.imaginary;
		double denominator = r.real*r.real + r.imaginary*r.imaginary;
		double realResult = realNumerator/denominator;
		double imagResult = imagNumerator/denominator;
		ComplexNumber quotient = new ComplexNumber(realResult,imagResult);
		return quotient;
	}

	public static void main(String[] args)
	{
		ComplexNumber x = new ComplexNumber(3.0,2.0);
		ComplexNumber y = new ComplexNumber(1.0,5.0);
		ComplexNumber result = C_arithmetic.combineLikeTerms(x,y);
		result.printNumber();

		ComplexNumber product = C_arithmetic.multiply(x,y);
		product.printNumber();

		ComplexNumber quot = C_arithmetic.divide(x,y);
		quot.printNumber();
	}
}
