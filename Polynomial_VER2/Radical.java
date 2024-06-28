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

	int _radicand;
	int _root;

	public Radical(Object expression, int root, int radicand, double coefficient)
	{
		this._p = (Polynomial)expression;
		this._radicand = radicand;
		this._root = root;
		this.activeRoot = true;
		this._coefficient = coefficient;
		Fraction f = new Fraction(radicand,root); 
	}

	private static ArrayList<Double> findSquaresUpTo(double value)
	{
		ArrayList<Double> squares = new ArrayList<Double>();
		squares.add(1.0);
		for (int i = 2; i <= (int)(value/2); i++)
			squares.add((double)(i*i));

		return squares;
	}

	public static ArrayList<Double> reduceToLowestSquare(double value)
	{
		ArrayList<Double> factors = new ArrayList<Double>();
		ArrayList<Double> squares = findSquaresUpTo(value);
		for (int j = 0; j < squares.size(); j++)
		{
			double nextSquare = squares.get(j)*squares.get(j);
			if (value%nextSquare == 0)
				factors.add(nextSquare);
		}
		System.out.println(factors);
		return squares;
	}

	private int identify(Object obj)
	{
		int type = 0;
		if (obj instanceof Polynomial)
			type = 0;
		else if (obj instanceof RationalExpression)
			type = 1;

		return type;
	}

	public void displayRadical(Object obj, Fraction r)
	{
		int direction = this.identify(obj);
		String fractionRoot = r.displayFraction();
		if (direction == 0)
		{
			System.out.print(fractionRoot + "-rt(");
			Polynomial p = (Polynomial)obj;
			p.printPolynomial();
			System.out.println(")");
		}
		else if (direction == 1)
		{
			RationalExpression expression = (RationalExpression)obj;
			System.out.print(fractionRoot + "-rt(");
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
		this._p.doubleDistribute(this._p,this._p);
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
		ArrayList<Double> possibleSquares = Radical.reduceToLowestSquare(500.0);
	}
}