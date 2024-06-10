import java.util.*;
import java.lang.Math.*;

public class Radical
{
	int _numerator = 1;
	int _denominator = 1;
	private Object algebraicExpression;
	private boolean activeRoot;
	private RationalExpression ratio;
	private double _coefficient;
	private Polynomial _p;

	public Radical(Object expression, int n, int d, double coefficient)
	{
		_p = (Polynomial)expression;
		_numerator = n;
		_denominator = d;
		activeRoot = true;
		_coefficient = coefficient;
		Fraction f = new Fraction(_numerator,_denominator); 
	}

	private String identify(Object obj)
	{
		String type = "";
		if (obj instanceof Polynomial)
			type = "Polynomial";
		else if (obj instanceof RationalExpression)
			type = "Rational";

		return type;
	}

	public void displayRadical(Object obj, Fraction r)
	{
		String direction = this.identify(obj);
		String fractionRoot = r.displayFraction();
		if (direction.equals("Polynomial"))
		{
			System.out.print(fractionRoot + "-rt(");
			Polynomial p = (Polynomial)obj;
			p.printPolynomial();
			System.out.println(")");
		}
		else if (direction.equals("Rational"))
		{
			RationalExpression expression = (RationalExpression)obj;
			System.out.print("sqrt(");
			expression.displayExpression();
			System.out.println(")");			
		}
	}

	public Object evaluate(Object expression, double value, int root)
	{
		Object obj = new Object();
		double result = 0.0;
		if (expression instanceof Polynomial)
		{
			Polynomial p = (Polynomial)expression;
			result = p.evaluate(value);
			if (result < 0)
			{
				ComplexNumber output = new ComplexNumber(0.0,Math.sqrt(1/root));
				obj = output;
			}
			else
				result = Math.sqrt(result);
				obj = result;
		}
		else if (expression instanceof RationalExpression)
		{
			RationalExpression r = (RationalExpression)expression;
			result = r.evaluate(value);
			Math.sqrt(result);
		}
		return obj;
	}

	public void nthRootPolynomial(Fraction root)
	{
		double nthRoot = root.toDecimal(root);
		this._p.power(this._p,root._num);
		this._p.distribute(this._p,this._p);
		for (int i = 0; i < this._p._exponents.size(); i++)
		{
			//if the exponent of each term is divisible by the root, then divide i-th exponent by that root
			if (this._p._exponents.get(i)%root._den == 0)
				this._p._exponents.set(i,this._p._exponents.get(i)/root._den);
		}
		this._p.simplify();
	}

	public void rationalize(int n, int d, double coeff)
	{
		//rationalizing a radical by multiplying to itself to remove it from the denominator and multiplying it to the numerator
		
	}

	public static void main(String[] args)
	{
		Polynomial p = Polynomial.generateRandomPolynomial("x",7,5,4);
		p.printPolynomial();
	}
}