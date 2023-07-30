import java.util.*;
import java.lang.Math.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolynomialArithmetic
{
	private String combineLikeTerms(String firstVariable, int firstExponent, double firstCoefficient, String secondVariable, int secondExponent, double secondCoefficient)
	{
		double coefficientSum = 0.0;
		if (firstVariable.equals(secondVariable) && firstExponent == secondExponent)
		{
			coefficientSum = firstCoefficient+secondCoefficient;
		}
		return String.valueOf(coefficientSum);
	}

	public double findTheRoot(Polynomial p, double r)
	{
		return 0;
	}

	public String doubleDistribute(Polynomial_v7 p, Polynomial_v7 q)
	{
		double[][] coefficientMatrix = new double[p.getTerms().size()][q.getTerms().size()];
		int[][] exponentMatrix = new int[p.getTerms().size()][q.getTerms().size()];

		int i = 1;
		int j = 1;
		//top left hand corner left blank
		coefficientMatrix[0][0] = 0.0;
		exponentMatrix[0][0] = 0;
		
		//set up edge of matrix for double distrubution
		for (i = 1; i <= p.getCoefficients().size()-1; i++)
		{
			coefficientMatrix[i][0] = p.getCoefficients().get(i);
			exponentMatrix[i][0] = p.getExponents().get(i);
		}


		for (j = 1; j <= q.getTerms().size()-1; j++)
		{
			coefficientMatrix[0][j] = q.getCoefficients().get(j);
			exponentMatrix[0][j] = q.getExponents().get(j);
		}

		i = 0;
		j = 0;
		for (i = 1; i <= p.getCoefficients().size()-1; i++)
		{
			for (j = 1; j <= q.getCoefficients().size()-1; j++)
			{
				//new product = coefficient x coefficient
				//new exponents are ADDED, exponent + exponent
				coefficientMatrix[i][j] = coefficientMatrix[i][0]*coefficientMatrix[0][j];
				exponentMatrix[i][j] = exponentMatrix[i][0]+exponentMatrix[0][j];
			}
		}
	}

	public static void main(String[] args)
	{
		System.out.println("hello");
		Polynomial_v7 x = new Polynomial_v7("x");
		Polynomial_v7 y = new Polynomial_v7("x");
		ArrayList<Object> data = new ArrayList<Object>();

		PolynomialArithmetic pa = new PolynomialArithmetic();
		data = pa.doubleDistribute(x,y);

		System.out.println(data.get(0).size());
		System.out.println(data.get(1).size());
	}
}