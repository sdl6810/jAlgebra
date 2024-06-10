import java.util.*;
import java.lang.Math.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

public class Polynomial
{
	String _variable;
	ArrayList<Double> _coefficients = new ArrayList<Double>();
	ArrayList<Double> _exponents = new ArrayList<Double>();
	ArrayList<Double> _zeroes = new ArrayList<Double>();
	ArrayList<Double> pascal_coefficients = new ArrayList<Double>();
	double higherPower = 1.0;
	boolean isNestedPolynomial = false;

	ArrayList<Double> innerElements = new ArrayList<Double>();
	ArrayList<Double> innerExponents = new ArrayList<Double>();
	String innerVariable = _variable;

	public Polynomial(String variable, ArrayList<Double> coefficients, ArrayList<Double> exponents)
	{
		this._variable = variable;
		this._exponents = exponents;
		this._coefficients = coefficients;
	}

	public boolean isNested()
	{
		if (this._exponents.size() > this._coefficients.size())
			isNestedPolynomial = true;
		else
			isNestedPolynomial = false;

		return isNestedPolynomial;
	}

	public ArrayList<Double> getCoefficients()
	{
		return this._coefficients;
	}

	public ArrayList<Double> getExponents()
	{
		return this._exponents;
	}

	private void raiseToHigherPower(double power)
	{
		this.higherPower = power;
	}

	public void sortInDescendingOrder()
	{
	    for (int i = 0; i < _exponents.size() - 1; i++)
	    {
	        for (int j = i + 1; j < _exponents.size(); j++)
	        {
	            if (_exponents.get(i) < _exponents.get(j))
	            {
	                // Swap exponents
	                double tempExponent = _exponents.get(i);
	                _exponents.set(i, _exponents.get(j));
	                _exponents.set(j, tempExponent);

	                // Swap coefficients
	                double tempCoefficient = _coefficients.get(i);
	                _coefficients.set(i, _coefficients.get(j));
	                _coefficients.set(j, tempCoefficient);
	            }
	        }
	    }
	}

	private static double gcd(double a, double b)
	{
		while (b != 0)
		{
			double temp = b;
			b = a%b;
			a = temp;
		}
		return a;
	}

	private static double findGCF(ArrayList<Double> values)
	{
		double result = values.get(0);
		for (int i = 1; i < values.size(); i++)
		{
			result = Polynomial.gcd(result,values.get(i));
		}
		return result;
	}

	private String constructTerm(double coefficient, double exponent)
	{
		String term = "";
		this.sortInDescendingOrder();
		if (exponent == 0)
		{
			term = Double.toString(coefficient);
		}
		else if (exponent == 1)
		{
			term = Double.toString(coefficient)+_variable;
		}
		else
		{
			if (coefficient == 0.0) 
				term = Double.toString(coefficient)+_variable+"^"+Double.toString(exponent);
			else
				term = Double.toString(coefficient)+_variable+"^"+Double.toString(exponent);
		}
		return term;
	}

	//method tests to see if two polynomials are the same thing
	public boolean isEqualTo(Polynomial p)
	{
		this.sortInDescendingOrder();
		p.sortInDescendingOrder();

		boolean areTheSame = false;
		boolean hasSameVariable = false;
		boolean hasSameCoefficients = false;
		boolean hasSameExponents = false;
		boolean hasSameLength = false;

		//test if polynomials have same variable
		if (this._variable.equals(p._variable))
			hasSameVariable = true;
		else
			hasSameVariable = false;

		if (this._exponents.size() == p._exponents.size())
		{
			//test if polynomials are the same size (have same number of exponents)
			int exponentCount = 0;
			int coefficientCount = 0;
			hasSameLength = true;
			//test if it has same exponents
			for (int i = 0; i < this._exponents.size(); i++)
			{
				if (this._exponents.get(i) == p._exponents.get(i))
					exponentCount++;
				if (exponentCount == this._exponents.size())
					hasSameExponents = true;
			}

			//test if polynomials have same coefficients
			for (int j = 0; j < this._coefficients.size(); j++)
			{
				if (this._coefficients.get(j) == p._coefficients.get(j))
					coefficientCount++;
				if (coefficientCount == this._coefficients.size())
					hasSameCoefficients = true;
			}
		}
		else
			hasSameLength = false;

		if (hasSameVariable == true && hasSameExponents == true && hasSameCoefficients == true)
			areTheSame = true;
		else
			areTheSame = false;

		return areTheSame;
	}

	public String printPolynomial()
	{
		ArrayList<String> terms = new ArrayList<String>();
		String polynomialString = "";
		if (this.isNested())
		{
			//include code here for nested polynomials
		}
		else
		{
			for (int i = 0; i < this._coefficients.size(); i++)
			{
				if (this._coefficients.get(i) == 0)
				{
					terms.add(this.constructTerm(this._coefficients.get(i),this._exponents.get(i)));
				}
				else
				{
					terms.add(this.constructTerm(this._coefficients.get(i),this._exponents.get(i)));
				}
			}

			for (int j = 0; j < terms.size(); j++)
			{
				if (this._coefficients.get(j) >= 0)
				{
					polynomialString = polynomialString + "+" + terms.get(j);
				}
				else if (this._coefficients.get(j) < 0)
				{
					polynomialString = polynomialString + terms.get(j);
				}
			}
		}
		return polynomialString;
	}

	public double evaluate(double input)
	{
		double polynomialValue = 0.0;
		for (int i = this._coefficients.size() - 1; i >= 0; i--)
		{
			polynomialValue = polynomialValue + _coefficients.get(i)*Math.pow(input,this._exponents.get(i));
		}
		return polynomialValue;
	}

	public double degree()
	{
		this.sortInDescendingOrder();
		double degree = this._exponents.get(0);
		return degree;
	}

	public void simplify()
	{
	    this.sortInDescendingOrder();
	    
	    ArrayList<Double> newCoefficients = new ArrayList<>();
	    ArrayList<Double> newExponents = new ArrayList<>();
	    
	    for (int i = 0; i < this._exponents.size(); i++) 
	    {
	        double exponent = this._exponents.get(i);
	        double coefficient = this._coefficients.get(i);
	        int index = newExponents.indexOf(exponent);
	        if (index != -1) 
	        {
	            // Add the coefficient to the existing term
	            newCoefficients.set(index, newCoefficients.get(index) + coefficient);
	        } 
	        else 
	        {
	            // Add a new term
	            newExponents.add(exponent);
	            newCoefficients.add(coefficient);
	        }
	    }
	    
	    // Update the coefficients and exponents arrays
	    this._coefficients = newCoefficients;
	    this._exponents = newExponents;
	}

	public void add(Polynomial p)
	{
		for (int i = 0; i < p._exponents.size(); i++)
		{
			this._exponents.add(p._exponents.get(i));
			this._coefficients.add(p._coefficients.get(i));
		}
		this.simplify();
	}

	public void negate()
	{
		for (int i = 0; i < this._exponents.size(); i++)
		{
			this._coefficients.set(i,-1*this._coefficients.get(i));
		}
	}

	public static Polynomial distribute(double c, double e, Polynomial p)
	{
		ArrayList<Double> newCoefficients = new ArrayList<Double>();
		ArrayList<Double> newExponents = new ArrayList<Double>();
		for (int i = 0; i < p._coefficients.size(); i++)
		{
			newCoefficients.add(c*p._coefficients.get(i));
			newExponents.add(e+p._exponents.get(i));
		}
		Polynomial result = new Polynomial(p._variable,newCoefficients,newExponents);
		result.simplify();
		return result;
	}

	//q's terms are being distributed and combined into u's polynomials
	//q is the outer polynomial (one of the left), q is the inner polynomial (one on the right)
	public static Polynomial doubleDistribute(Polynomial q, Polynomial u)
	{
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		Polynomial w = new Polynomial(q._variable,c,e);
		Polynomial x = new Polynomial(q._variable,c,e);
		for (int j = 0; j < q._coefficients.size(); j++)
		{
			w = p.distribute(q._coefficients.get(j),q._exponents.get(j),u);
			w.simplify();
			x.add(w);
		}

		x.simplify();
		return x;
	}

	public static Polynomial power(Polynomial p, int exponent)
	{
		Polynomial q = p;
		for (int i = 2; i < exponent; i++)
			q = Polynomial.doubleDistribute(q,p);

		return q;
	}

	private boolean isComplex(Object root)
	{
		if(root instanceof ComplexNumber)
		{
			return true;
		}
		else
			return false;
	}

	private boolean isReal(Object root)
	{
		if (root instanceof Number)
		{
			return true;
		}
		else
			return false;
	}

	public ArrayList<Object> solveQuadratic(Polynomial p)
	{
		p.sortInDescendingOrder();
		p.simplify(); //to take care of any like terms that appear in the quadratic
		ArrayList<Object> roots = new ArrayList<>();
		if ((p._coefficients.size() == 1))
		{
			roots.add(Math.sqrt(p._coefficients.get(0)));
			roots.add((-1)*Math.sqrt(p._coefficients.get(0)));
		}
		if ((p._coefficients.size() == 2))
		{
			double binomialRoot = (-1)*p._coefficients.get(1)/p._coefficients.get(0);
			System.out.println(binomialRoot);
			System.out.println(binomialRoot < 0);
			/*0 = ax^2 + b
			  (-b/a) = p/m Math.sqrt(x^2)
			  p/m sqrt((-b/a)) = x*/
			if (p._exponents.get(1) == 0)
			{
				if (binomialRoot < 0)
				{
					roots.add(new ComplexNumber(0,binomialRoot));
					roots.add(new ComplexNumber(0,(-1)*binomialRoot));
				}
			}
			/*0 = ax^2 + bx
			  0 = x(ax + b)*/
			if (p._exponents.get(1) == 1)
			{
				roots.add(0.0);
				if (p._coefficients.get(1) > 0)
					roots.add((-1)*p._coefficients.get(1)/p._coefficients.get(0));
				else if (p._coefficients.get(1) < 0)
					roots.add(p._coefficients.get(1)/p._coefficients.get(0));
			}
		}
		/*0 = ax^2 + bx + c*/
		if (p._coefficients.size() >= 3)
		{//check if there are enough coefficients
			p.simplify();
			double discriminant = Math.pow(p._coefficients.get(1),2) - 4*p._coefficients.get(0)*p._coefficients.get(2);
			if (discriminant < 0)
			{
				ComplexNumber c1 = new ComplexNumber((-1*p._coefficients.get(1))/(2*p._coefficients.get(0)),-1*Math.sqrt(Math.abs(discriminant))/(2*p._coefficients.get(0)));
				ComplexNumber c2 = new ComplexNumber((-1*p._coefficients.get(1))/(2*p._coefficients.get(0)),Math.sqrt(Math.abs(discriminant))/(2*p._coefficients.get(0)));
				roots.add(c1);
				roots.add(c2);
			}
			else if (discriminant == 0)
			{
				roots.add(-1*p._coefficients.get(1)/(2*p._coefficients.get(1)));
			}
			else if (discriminant > 0)
			{
				roots.add(((-1*p._coefficients.get(1)-1*Math.sqrt(discriminant))/(2*p._coefficients.get(0))));
				roots.add(((-1*p._coefficients.get(1)+1*Math.sqrt(discriminant))/(2*p._coefficients.get(0))));
			}
		}
		return roots;
	}

	//apply intermediate value theorem
	private boolean applyIVT(Polynomial p, double a, double b)
	{
		boolean rootExists = false;
		double f_of_a = p.evaluate(a);
		double f_of_b = p.evaluate(b);

		if ((f_of_a > 0) && (f_of_b < 0) || (f_of_a < 0) && (f_of_b > 0))
			rootExists = true;
		else
			rootExists = false;

		return rootExists;
	}

	//apply Rational Zero Theorem to find possible real zeroes
	public ArrayList<Double> findRationalZeroes()
	{
		ArrayList<Double> c = this.getCoefficients();
		ArrayList<Double> actualZeroes = new ArrayList<Double>();
		int lastTerm = c.size()-1;
		ArrayList<Double> possibleZeroes = new ArrayList<Double>();

		//factors of leading coefficient
		ArrayList<Double> leadingCoeffFactors = generic_math.factor(c.get(0));

		//factors of last term
		ArrayList<Double> lastTermFactors = generic_math.factor(Math.abs(c.get(lastTerm)));

		//include negative factors
		int firstSize = leadingCoeffFactors.size();
		int lastSize = lastTermFactors.size();
		int k = 0;

		while (k < firstSize)
		{
			leadingCoeffFactors.add(-1*leadingCoeffFactors.get(k));
			k++;
		}

		int m = 0;
		while (m < lastSize)
		{
			lastTermFactors.add(-1*lastTermFactors.get(m));
			m++;
		}

		//generate complete list of all possible factors
		for (int i = 0; i < lastSize; i++)
			for (int j = 0; j < firstSize; j++)
				possibleZeroes.add(lastTermFactors.get(i)/leadingCoeffFactors.get(j));

		//go through each candidate and perform synthetic division with each one
		for (k = 0; k < possibleZeroes.size(); k++)
		{
			if (this.syntheticDivide(possibleZeroes.get(k)) == true)
				actualZeroes.add(possibleZeroes.get(k));
		}
		System.out.println("Possible zeroes: " + possibleZeroes);
		System.out.println("Last term factors " + lastTermFactors);
		System.out.println("Leading coefficient " + leadingCoeffFactors);
		return actualZeroes;
	}

	public void insertMissingValues()
	{
	    ArrayList<Double> newExponents = new ArrayList<>();
	    ArrayList<Double> newCoefficients = new ArrayList<>();

	    for (int i = 0; i < this._exponents.size() - 1; i++)
	    {
	        double exponent = this._exponents.get(i);
	        double nextExponent = this._exponents.get(i + 1);
	        double exponentDifference = Math.abs(nextExponent - exponent);
	        newExponents.add(exponent);
	        newCoefficients.add(this._coefficients.get(i));

	        if (exponentDifference > 1)
	        {
	            for (int j = 1; j < exponentDifference; j++)
	            {
	                newExponents.add(exponent-j);
	                newCoefficients.add(0.0); // Add corresponding coefficients for new exponents
	            }
	        }
	    }

	    // Add the last exponent and coefficient
	    newExponents.add(this._exponents.get(this._exponents.size() - 1));
	    newCoefficients.add(this._coefficients.get(this._coefficients.size() - 1));

	    // Update the polynomial with the new exponents and coefficients
	    this._exponents = newExponents;
	    this._coefficients = newCoefficients;
	}

	public boolean syntheticDivide(double zero) 
	{
	    boolean zeroIsTrue = false;
	    double sum = 0.0;
	    ArrayList<Double> roots = new ArrayList<Double>();
	    ArrayList<Double> newPolynomial = new ArrayList<Double>();
	    this.sortInDescendingOrder();

	    double degree = this.degree();
	    ArrayList<Double> c = this.getCoefficients();
	    this.insertMissingValues();

	    for (int i = 0; i < c.size(); i++) 
	    {
	        if (i == 0)
	            newPolynomial.add(c.get(0));
	        else
	            newPolynomial.add(c.get(i) + zero * newPolynomial.get(i - 1));
	        System.out.println(newPolynomial);
	    }

	    int lastTerm = newPolynomial.size() - 1;
	    if (newPolynomial.get(lastTerm) == 0)
	        zeroIsTrue = true;
	    else
	        zeroIsTrue = false;
	    return zeroIsTrue;
	}

	public ArrayList<Object> findZeroes(Polynomial p, double value)
	{
		//if there is no variable in the polynomial
		//start putting exception handling here for dealing with things that are too simple to be factored
		ArrayList<Object> factors = new ArrayList<>();
		p.sortInDescendingOrder();
		p.simplify();
		double coeffGCF = 0.0;
		double gcfExponent = 0.0;
		double rightHandSide = 0.0;
		ArrayList<Double> c = p.getCoefficients();
		if (!p._exponents.isEmpty())
		{
			double degree = p.degree();
			if (degree == 0)
			{
				System.out.println("This is a constant function.  It does not have a root.");
			}
			else if (degree == 1)
			{
				if (p._coefficients.size() >= 2)
				{//check if there are enough coefficients
					if (c.get(1) > 0)
					{
						rightHandSide = value - c.get(1);

					}
					else if (value < 0)
					{
						rightHandSide = value + c.get(1);
					}

				}
				rightHandSide = rightHandSide/c.get(0);
				factors.add(rightHandSide);			
			}
			else if (degree == 2)
			{
				factors = p.solveQuadratic(p);
				System.out.println(factors);
				if ((factors.get(0) instanceof ComplexNumber))
				{
				    ComplexNumber complexFactor1 = (ComplexNumber) factors.get(0);
				    complexFactor1.printNumber();
				}
				if (factors.size() > 1 && factors.get(1) instanceof ComplexNumber) 
				{
				    ComplexNumber complexFactor2 = (ComplexNumber) factors.get(1);
				    complexFactor2.printNumber();
				}
			}

			else if (degree >= 3)
			{
				//factoring variables
				//check if there are enough coefficients
				double minExponent = Collections.min(p._exponents);
				System.out.println(minExponent);
				if (p._coefficients.size() >= p._exponents.size())
				{
					for (double exp : p._exponents) 
					{
					    if (exp != 0 && exp < minExponent) 
					    {
					        minExponent = exp;
					    }
					}
				}
			}	
		}
		return factors;
	}

	private boolean checkValidity(Object solution, Polynomial rhs)
	{
		double lhsValue = this.evaluate((double)solution);
		double rhsValue = rhs.evaluate((double)solution);

		if (rhsValue == 0 || lhsValue < rhsValue)
			return true;
		else
			return false;
	}

	public static Polynomial generateRandomPolynomial(String variable, int terms, int max_coeff, int max_exp)
	{
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		int i = 0;
		while (i <= terms)
		{
			c.add((double)generic_math.randbetween(0,max_coeff));
			e.add((double)generic_math.randbetween(0,max_exp));
			i++;
		}
		Polynomial random = new Polynomial(variable,c,e);
		random.simplify();
		random.sortInDescendingOrder();
		return random;
	}

	private int locateSpecificTerm(double coeff, double exponent)
	{
		ArrayList<Double> e = this.getExponents();
		int index;
		for (index = 0; index < this._exponents.size(); index++)
		{
			if (e.get(index) == coeff && e.get(index) == exponent)
				break;
		}
		return index;
	}

	/*ArrayList<Object> c will have mostly double elements except for a Polynomial in between them
	this Polynomial will be the nested Polynomial inside the larger one.  It will be assumed that
	nothing has been distributed or simplified to this nested polynomial.*/
	public void uSubstitution(ArrayList<Double> coeffIdentifier, ArrayList<Double> exponentIdentifier)
	{
		
	}

	public static void main(String[] args)
	{
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		
		ArrayList<Double> c2 = new ArrayList<Double>();
		ArrayList<Double> e2 = new ArrayList<Double>();
		ArrayList<Double> c3 = new ArrayList<Double>();
		ArrayList<Double> e3 = new ArrayList<Double>();
		ArrayList<Double> c4 = new ArrayList<Double>();
		ArrayList<Double> e4 = new ArrayList<Double>();

		c.add(8.0);
		c.add(3.0);
		e.add(4.0);
		e.add(1.0);

		c2.add(6.0);
		c2.add(15.0);
		c2.add(34.0);
		e2.add(3.0);
		e2.add(1.0);
		e2.add(0.0);

		c3.add(3.0);
		c3.add(8.0);
		c4.add(2.0);
		c4.add(-7.0);
		e3.add(2.0);
		e3.add(1.0);
		e4.add(1.0);
		e4.add(0.0);

		Polynomial p = new Polynomial("x",c,e);
		Polynomial q = new Polynomial("x",c2,e2);
		Polynomial s = new Polynomial("x",c3,e3);
		Polynomial t = new Polynomial("x",c4,e4);
	}
}