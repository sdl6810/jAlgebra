import java.util.*;
import java.lang.Math.*;

public class RationalExpression
{
	//p is the numerator polynomial and q is the denominator polynomial
	String _variable = "";
	ArrayList<Polynomial> _num = new ArrayList<Polynomial>();
	ArrayList<Polynomial> _denom = new ArrayList<Polynomial>();
	
	public RationalExpression(ArrayList<Polynomial> num, ArrayList<Polynomial> denom)
	{
		this._num = num;
		this._denom = denom;
	}

	public void displayExpression()
	{
		for (int i = 0; i < this._num.size(); i++)
		{
			
			System.out.print("(");
			this._num.get(i).printPolynomial();
			System.out.print(")");
		}
		System.out.println("\n");
		System.out.println("--------------------------------------------------------------------");

		for (int j = 0; j < this._denom.size(); j++)
		{
			System.out.print("(");
			this._denom.get(j).printPolynomial();
			System.out.print(")");
		}
		System.out.println("\n");
	}

	public double evaluate(double value)
	{
		double numProduct = 1.0;
		double denomProduct = 1.0;
		double result;
		for (int i = 0; i < this._num.size(); i++)
		{
			numProduct = numProduct*this._num.get(i).evaluate(value);
		}
		for (int j = 0; j < this._denom.size(); j++)
		{
			denomProduct = denomProduct*this._denom.get(j).evaluate(value);
		}
		result = numProduct/denomProduct;
		return result;
	}

	public void displayComponents()
	{
		this._num.get(0).printPolynomial();
		this._denom.get(0).printPolynomial();
	}

	public void simplify()
	{
		//cycle through the numerator
		for (int i = 0; i < this._num.size(); i++)
		{
			//cycle through each polynomial in the numerator
			//cycle through denominator
			for (int k = 0; k < this._denom.size(); k++)
			{
			//check if polynomials are equal
				if ((this._num.get(i).isEqualTo(this._denom.get(k))))
				{
					//if they are equal, remove them from top and bottom
					//"cancel them out"
					this._num.remove(i);
					this._denom.remove(k);
					// Adjust the indices after removal
                	i--;
                	break; // Exit the inner loop since we found a match
				}	
			}
		}
	}

	public RationalExpression multiply(RationalExpression p, RationalExpression q)
	{
		ArrayList<Double> cEmpty = new ArrayList<Double>();
		ArrayList<Double> eEmpty = new ArrayList<Double>();
		Polynomial newNum = new Polynomial(p._num.get(0)._variable,cEmpty,eEmpty);
		Polynomial newDenom = new Polynomial(p._denom.get(0)._variable,cEmpty,eEmpty);

		ArrayList<Polynomial> numList = new ArrayList<Polynomial>();
		ArrayList<Polynomial> denomList = new ArrayList<Polynomial>();
		numList.add(newNum);
		denomList.add(newDenom);

		p.simplify();
		q.simplify();
		for (int i = 0; i < q._num.size(); i++)
			p._num.add(q._num.get(i));

		for (int j = 0; j < q._denom.size(); j++)
			p._denom.add(q._denom.get(j));

		p.simplify();

		//double distribute the terms
		for (int k = 0; k < p._num.size(); k++)
			newNum = Polynomial.distribute(newNum,p._num.get(k));

		for (int m = 0; m < p._denom.size(); m++)
			newDenom = Polynomial.distribute(newDenom,p._denom.get(m));

		//then combine like terms to simplify again
		newNum.simplify();
		newDenom.simplify();

		RationalExpression result = new RationalExpression(numList,denomList);
		return result;
	}

	public RationalExpression power(RationalExpression p, int x)
	{
		ArrayList<Polynomial> temp = new ArrayList<Polynomial>();
		ArrayList<Polynomial> temp2 = new ArrayList<Polynomial>();
		RationalExpression exp = new RationalExpression(temp,temp2);
		for (int i = 1; i <= x; i++)
			exp = this.multiply(p,p);

		return exp;
	}

	private void reciprocate()
	{
		ArrayList<Polynomial> temp = this._denom;
		this._denom = this._num;
		this._num = temp;
	}

	public RationalExpression divide(RationalExpression p, RationalExpression q)
	{
		q.reciprocate();
		p.multiply(p,q);
		p.simplify();
		return p;
	}

	public static void main(String[] args)
	{
		String variable = "x";
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();

		ArrayList<Double> c2 = new ArrayList<Double>();
		ArrayList<Double> e2 = new ArrayList<Double>();

		ArrayList<Double> c3 = new ArrayList<Double>();
		ArrayList<Double> e3 = new ArrayList<Double>();

		ArrayList<Double> c4 = new ArrayList<Double>();
		ArrayList<Double> e4 = new ArrayList<Double>();
		c.add(7.0);
		c.add(9.0);
		c.add(-5.0);
		e.add(2.0);
		e.add(1.0);
		e.add(0.0);

		c2.add(14.0);
		c2.add(-9.0);
		c2.add(-1.0);
		e2.add(2.0);
		e2.add(1.0);
		e2.add(0.0);

		c3.add(15.0);
		c3.add(29.0);
		c3.add(-17.0);

		e3.add(2.0);
		e3.add(1.0);
		e3.add(0.0);

		c4.add(19.0);
		c4.add(24.0);
		c4.add(18.0);
		e4.add(2.0);
		e4.add(1.0);
		e4.add(0.0);
		Polynomial p = new Polynomial(variable,c,e);
		Polynomial s = new Polynomial(variable,c2,e2);
		Polynomial q = new Polynomial(variable,c3,e3);
		Polynomial t = new Polynomial(variable,c4,e4);

		ArrayList<Polynomial> someFactor = new ArrayList<Polynomial>();
		ArrayList<Polynomial> anotherFactor = new ArrayList<Polynomial>();
		someFactor.add(p);
		someFactor.add(q);
		someFactor.add(s);
		someFactor.add(s);

		anotherFactor.add(q);
		anotherFactor.add(t);


		RationalExpression r = new RationalExpression(someFactor,anotherFactor);
		r.reciprocate();
		r.simplify();
		r.displayExpression();
		double x = r.evaluate(1.0);
		System.out.println(x);
	}
}