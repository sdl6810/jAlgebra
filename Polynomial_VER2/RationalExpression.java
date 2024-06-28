import java.util.*;
import java.lang.Math.*;

public class RationalExpression
{
	//create a method that reduces numerators and/or denominators when the factors are raised to a power

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
			System.out.print(this._num.get(i).printPolynomial());
			System.out.print(")");
		}
		System.out.println("\n");
		System.out.println("--------------------------------------------------------------------");

		for (int j = 0; j < this._denom.size(); j++)
		{
			System.out.print("(");
			System.out.print(this._denom.get(j).printPolynomial());
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

	public ArrayList<Object> findCriticalValues(RationalExpression r)
	{
		r.simplify();
		ArrayList<Object> values = new ArrayList<>();
		for (int i = 0; i < r._denom.size(); i++)
			values.add(r._denom.get(i));

		return values;
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
			newNum = Polynomial.doubleDistribute(newNum,p._num.get(k));

		for (int m = 0; m < p._denom.size(); m++)
			newDenom = Polynomial.doubleDistribute(newDenom,p._denom.get(m));

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

	private boolean productsAreEqualTo(ArrayList<Polynomial> factors)
	{
		int numberOfEqualTerms = 0;
		boolean productsEqual = false;
		//if there are less factors than in _denom
		if (this._denom.size() != factors.size())
		{
			if (this._denom.size() > factors.size())
				productsEqual = false;
			else if (this._denom.size() < factors.size())
				productsEqual = false;
		}
		//if the number of factors are equal, check if each factor is the same
		else if (this._denom.size() == factors.size())
		{
			for (int i = 0; i < this._denom.size(); i++)
			{
				for (int j = 0; j < factors.size(); j++)
				{
					if (this._denom.get(i).isEqualTo(factors.get(j)))
						numberOfEqualTerms++;
				}
			}
			if (numberOfEqualTerms == this._denom.size())
				productsEqual = true;
		}
		
		return productsEqual;
	}

	//tests if a polynomial is in a list of factors
	public static boolean isInFactorList(double constant, double exponent, ArrayList<Polynomial> array)
	{
		boolean isInList = false;
		for (int i = 0; i < array.size(); i++)
		{
			for (int j = 0; j < array.get(i)._exponents.size(); j++)
			{
				if ((constant == array.get(i)._coefficients.get(j)) && (exponent == array.get(i)._exponents.get(j)))
					isInList = true;
			}
		}
		return isInList;
	}

	public static ArrayList<Polynomial> generateLCM(ArrayList<Polynomial> factorList1, ArrayList<Polynomial> factorList2) 
	{
	    ArrayList<Polynomial> lcm = new ArrayList<>();

	    // Add all polynomials from factorList1
	    for (Polynomial poly : factorList1)
	    {
	        if (!isInFactorList(poly, factorList2)) 
	            lcm.add(poly);
	    }

	    // Add all polynomials from factorList2 that are not already in lcm
	    for (Polynomial poly : factorList2)
	    {
	        if (!isInFactorList(poly, lcm))
	            lcm.add(poly);
	    }

	    return lcm;
	}

	private static boolean isInFactorList(Polynomial poly, ArrayList<Polynomial> list)
	{
	    for (Polynomial p : list) 
	    {
	        if (p.isEqualTo(poly)) 
	            return true;
	    }
	    return false;
	}

	public void negateRE()
	{
		//will multiply the first term of the numerator by one
		//this negation will distribute to the rest of the terms in the numerator and affect the overall value of the rational expression
		this._num.get(0).negate();
	}

	public static RationalExpression add(RationalExpression left, RationalExpression right)
	{
		int sameFactors = 0;
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		ArrayList<Polynomial> lcm = RationalExpression.generateLCM(left._denom,right._denom);
		Polynomial finalNumerator = new Polynomial(left._num.get(0)._variable,c,e);
		ArrayList<Polynomial> n = new ArrayList<Polynomial>();
		RationalExpression result = new RationalExpression(n, lcm);

		boolean denominatorsAreEqual = left.productsAreEqualTo(right._denom);
		Polynomial lhsProduct = new Polynomial(left._num.get(0)._variable,c,e);
		Polynomial rhsProduct = new Polynomial(left._num.get(0)._variable,c,e);

		if (denominatorsAreEqual)
		{
			//if there is only one factor in the left hand side
			if (left._num.size() == 1)
				lhsProduct = left._num.get(0);
			else
			{
				lhsProduct = Polynomial.doubleDistribute(left._num.get(0),left._num.get(1));
				//if there are more than two factors
				if (left._num.size() > 2)
				{
					for (int k = 2; k < left._num.size(); k++)
						lhsProduct = Polynomial.doubleDistribute(lhsProduct,left._num.get(k));
				}
			}

			//if there is only one factor in the right hand side
			if (right._num.size() == 1)
				rhsProduct = right._num.get(0);
			else
			{
				rhsProduct = Polynomial.doubleDistribute(right._num.get(0),right._num.get(1));
				//if there are more than two factors in the right hand side
				if (right._num.size() > 2)
				{
					for (int m = 2; m < right._num.size(); m++)
						rhsProduct = Polynomial.doubleDistribute(rhsProduct,right._num.get(m));
				}
				lhsProduct.sortInDescendingOrder();
				lhsProduct.simplify();
				rhsProduct.sortInDescendingOrder();
				rhsProduct.simplify();
			}

			lhsProduct.add(rhsProduct);
			lhsProduct.sortInDescendingOrder();
			lhsProduct.simplify();
			result._num.clear();
			result._num.add(lhsProduct);
		}
		else if (!denominatorsAreEqual)
		{
			//"multiply" each of the LCM factors to the numerators **DO NOT DOUBLE DISTRIBUTE YET**
			for (int i = 0; i < lcm.size(); i++)
			{
				left._num.add(lcm.get(i));
				right._num.add(lcm.get(i));
			}

			//simplify both expressions by removing multiples of one
			left.simplify();
			right.simplify();

			//what should happen if it is only one factor instead of two?
			Polynomial leftNumerator = Polynomial.doubleDistribute(left._num.get(0),left._num.get(1));
			Polynomial rightNumerator = Polynomial.doubleDistribute(right._num.get(0),right._num.get(1));

			if (left._denom.size() > 2)
			{
				for (int j = 2; j < left._denom.size(); j++)
					leftNumerator = Polynomial.doubleDistribute(leftNumerator,left._num.get(j));
			}

			if (right._denom.size() > 2)
			{
				for (int k = 2; k < right._denom.size(); k++)
					rightNumerator = Polynomial.doubleDistribute(rightNumerator,right._num.get(k));
			}
			leftNumerator.sortInDescendingOrder();
			leftNumerator.simplify();
			rightNumerator.sortInDescendingOrder();
			rightNumerator.simplify();
			leftNumerator.add(rightNumerator);
			leftNumerator.sortInDescendingOrder();
			leftNumerator.simplify();
		}

		return result;
	}

	public static RationalExpression subtract(RationalExpression left, RationalExpression right)
	{
		right.negateRE();
		return RationalExpression.add(left,right);
	}

	public static ArrayList<Polynomial> determinePartialFractions(ArrayList<RationalExpression> expressions)
	{
		ArrayList<Polynomial> algebraicCoefficients = new ArrayList<Polynomial>();

		return algebraicCoefficients;
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

		System.out.println(someFactor.get(0).printPolynomial());
		System.out.println(anotherFactor.get(0).printPolynomial());

		RationalExpression r = new RationalExpression(someFactor,anotherFactor);
		r.displayExpression();
	}
}