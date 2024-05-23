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
	ArrayList<Object> higherPower = new ArrayList<>();

	public Polynomial(String variable, ArrayList<Double> coefficients, ArrayList<Double> exponents)
	{
		this._variable = variable;
		this._exponents = exponents;
		this._coefficients = coefficients;
	}

	private void raiseToHigherPower(double power)
	{
		this.higherPower.add(power);
		this.higherPower.add(this._exponents);
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

	public String constructTerm(double coefficient, double exponent)
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
		else if (coefficient == 0)
		{
			term = "0";
		}
		else
		{
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

	public void printPolynomial()
	{
		String polynomialString = "";
		ArrayList<String> terms = new ArrayList<String>();
		for (int i = 0; i < _coefficients.size(); i++)
		{
			terms.add(this.constructTerm(_coefficients.get(i),_exponents.get(i)));
		}

		for (int j = 0; j < terms.size(); j++)
		{
			if (this._coefficients.get(j) > 0)
			{
				polynomialString = polynomialString + "+" + terms.get(j);
			}
			else if (this._coefficients.get(j) < 0)
			{
				polynomialString = polynomialString + terms.get(j);
			}
		}
		System.out.print(polynomialString);
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

	public static void add(Polynomial a, Polynomial b)
	{
		for (int i = 0; i < b._exponents.size(); i++)
		{
			a._exponents.add(b._exponents.get(i));
			a._coefficients.add(b._coefficients.get(i));
		}
		a.simplify();
	}

	public static void negate(Polynomial a)
	{
		for (int i = 0; i < a._exponents.size(); i++)
		{
			a._coefficients.set(i,-1*a._coefficients.get(i));
		}
	}

	public static Polynomial distribute(Polynomial a, Polynomial b) 
	{
	    ArrayList<Double> newCoefficients = new ArrayList<>();
	    ArrayList<Double> newExponents = new ArrayList<>();

	    for (int i = 0; i < a._coefficients.size(); i++) 
	    {
	        for (int j = 0; j < b._coefficients.size(); j++) 
	        {
	            double newCoefficient = a._coefficients.get(i) * b._coefficients.get(j);
	            double newExponent = a._exponents.get(i) + b._exponents.get(j);
	            newCoefficients.add(newCoefficient);
	            newExponents.add(newExponent);
	        }
	    }

	    Polynomial result = new Polynomial(a._variable, newCoefficients, newExponents);
	    result.simplify();
	    return result;
	}

	public Polynomial power(Polynomial p, int exponent)
	{
		for (int i = 1; i < exponent; i++)
			Polynomial.distribute(p,p);
		return p;
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
		double discriminant = Math.pow(p._coefficients.get(1),2) - 4*p._coefficients.get(0)*p._coefficients.get(2);
		ArrayList<Object> roots = new ArrayList<>();
		if (discriminant < 0)
		{
			ComplexNumber c1 = new ComplexNumber((-1*p._coefficients.get(1)/(2*p._coefficients.get(1))),-1*Math.sqrt(discriminant)/(2*p._coefficients.get(0)));
			ComplexNumber c2 = new ComplexNumber((-1*p._coefficients.get(1)/(2*p._coefficients.get(1))),Math.sqrt(discriminant)/(2*p._coefficients.get(0)));
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
		return roots;
	}

	public ArrayList<Object> solve(Polynomial p, double value)
	{
		//if there is no variable in the polynomial
		//start putting exception handling here for dealing with things that are too simple to be factored
		ArrayList<Object> factors = new ArrayList<>();
		p.sortInDescendingOrder();
		p.simplify();
		double coeffGCF = 0.0;
		double gcfExponent = 0.0;
		double rightHandSide = 0.0;
		if (p.degree() == 0)
		{
			//constant term
		}
		else if(p.degree() == 1)
		{
			if (p._coefficients.get(1) > 0)
			{
				rightHandSide = value - p._coefficients.get(1);
			}
			else if (value < 0)
			{
				rightHandSide = value + p._coefficients.get(1);
			}

			rightHandSide = rightHandSide/p._coefficients.get(0);
			factors.add(rightHandSide);			
		}
		else if (p.degree() == 2)
		{
			factors = p.solveQuadratic(p);
			String complexConjugate, complexConjugate2 = "";
			if ((factors.get(0) instanceof ComplexNumber) && (factors.get(1) instanceof ComplexNumber))
			{
				for (Object factor : factors) 
				{
			        if (factor instanceof ComplexNumber) 
			        {
			        	//says unknown error when it should print out the complex number
			            ComplexNumber complexFactor = (ComplexNumber) factor;
			            complexFactor.printNumber();
		        	}
		        }
			}
			factors.set(0,factors.get(0));
			factors.set(1,factors.get(1));
		}
		else if (p.degree() >= 3)
		{
			//factoring variables really depends on finding smallest exponent and subtracting that minimum from every other variable incudling itself
			// Finding the smallest non-zero exponent
			double minExponent = Double.MAX_VALUE;
			for (double exp : p._exponents) 
			{
			    if (exp != 0 && exp < minExponent) 
			    {
			        minExponent = exp;
			    }
			}
			gcfExponent = minExponent;

			coeffGCF = Polynomial.findGCF(p._coefficients);

			for (int i = 0; i < p._exponents.size(); i++)
			{
				p._exponents.set(i,p._exponents.get(i)-gcfExponent);
				factors.add(p._coefficients.get(i)/coeffGCF);
			}
		}
		System.out.println(gcfExponent);
		System.out.println(coeffGCF);
		return factors;
	}

	//this inner class is for a special kind of polynomials: polynomials that are in factored form
	public class FactoredForm
	{
		String _variable = "";
		ArrayList<Double> _zeroes = new ArrayList<Double>();
		ArrayList<Double> _exponents = new ArrayList<Double>();
		ArrayList<Double> _coefficients = new ArrayList<Double>();

		public FactoredForm(String variable, ArrayList<Double> zeroes, ArrayList<Double> exponents, ArrayList<Double> coefficients)
		{
			this._variable = variable;
			this._zeroes = zeroes;
			this._exponents = exponents;
			this._coefficients = coefficients;
		}
	}

	public static void main(String[] args)
	{
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		ArrayList<String> s = new ArrayList<String>();
		
		c.add(2.0);
		c.add(1.0);
		c.add(-3.0);
		e.add(2.0);
		e.add(1.0);
		e.add(0.0);

		Polynomial p = new Polynomial("x",c,e);
		//p.printPolynomial();

		ArrayList<Object> f = p.solve(p,0);
		//p.printPolynomial();
		p.sortInDescendingOrder();
		p.printPolynomial();
		p.simplify();

		System.out.println(f);

		boolean equalToItself = p.isEqualTo(p);
		System.out.println(equalToItself);
	}
}