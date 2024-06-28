import java.util.*;
import java.lang.Math.*;

public class Integration
{
	double lowerBound;
	double upperBound;
	Object algebraicExpression;

	public Integration(Object expression, double ub, double lb)
	{
		algebraicExpression = expression;
		lowerBound = lb;
		upperBound = ub;
	}

	public static Polynomial indefiniteIntegral(Polynomial p)
	{
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		Polynomial integrate = new Polynomial(p._variable,c,e);

		for (int i = 0; i < p._exponents.size(); i++)
		{
			integrate._coefficients.add(p._coefficients.get(i)/(p._exponents.get(i)+1));
			integrate._exponents.add(p._exponents.get(i)+1);
		}
		return integrate;
	}

	public static double definiteIntegral(Polynomial p, double upper, double lower)
	{
		Polynomial integrated = indefiniteIntegral(p);
		double difference = integrated.evaluate(upper) - integrated.evaluate(lower);
		return difference;
	}	

	public static void main(String[] args)
	{

	}
}