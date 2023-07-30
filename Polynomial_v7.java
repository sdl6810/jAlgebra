/*
FUTURE GOALS OF POLYNOMIAL CLASS:
- determine what variables are in the polynomial
- factor any expression given
- EXPAND any given factored form
- combine like terms and simplify
*/

import java.util.*;
import java.lang.Math.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial_v7
{
	public ArrayList<String> terms = new ArrayList<String>();
	public ArrayList<Double> coefficients = new ArrayList<Double>();
	public ArrayList<Integer> exponents = new ArrayList<Integer>();

	//set at zero until constructor will set it to a value using the getDegree() method
	private int degree = 0;
	private String[] types = {"constant","linear","quadratic","cubic","quartic","quintic"};
	private String kindOfPolynomial = null;
	private String[] functionPrefixes = {"sin(", "cos(", "tan(", "ln(", "log(", "exp(", "asin(", "acos(", "atan", "sqrt("};
	public String expression = null;

	Scanner keyboard = new Scanner(System.in);

	public Polynomial_v7(String variable)
	{
		//throw exception that if any function prefixes are contained in the given "polynomial" then Java should state this as such and have the user to try again
		System.out.println("Enter your polynomial.");
		System.out.println("Use decimals or approximate decimals for rational values.");
		System.out.println("Be sure to use parentheses whenever you can to clarify the operation as much as possible.");

		this.expression = keyboard.next();
		this.extractTerms();
		this.extractCoefficients(variable);
		this.extractExponents(variable);
		this.determineDegree();
	}

	public void sayHello()
	{
		System.out.println("Hello!");
	}

	private void extractTerms()
	{
		int currentSplitIndex = 0;
		int previousSplitIndex = 0;
		String result = null;
		String lastTerm = null;

		//identify and separate each of the terms as each element in the ArrayList
		int i = 0;
		int tempLocation = 0;
		while (i < this.expression.length())
		{
			if (this.expression.charAt(i) == '+' || this.expression.charAt(i) == '-')
			{
				StringBuilder newSubstring = new StringBuilder();
				for (int j = previousSplitIndex; j <= i-1; j++)
				{
					newSubstring.append(this.expression.charAt(j));
					result = newSubstring.toString();
					System.out.println(newSubstring);
				}
			this.terms.add(result);
			tempLocation = i;
			previousSplitIndex = tempLocation;
			}
			else
			{
				StringBuilder builder = new StringBuilder();
				for (int k = previousSplitIndex; k < this.expression.length(); k++)
				{
					builder.append(this.expression.charAt(k));
					lastTerm = builder.toString();
				}
			}
			previousSplitIndex = tempLocation;
			i++;
		}
		this.terms.add(lastTerm);
	}

	private void extractExponents(String variable)
	{
		for (int e = 0; e <= this.terms.size()-1; e++)
		{
			if (this.terms.get(e).contains("^"))
			{
				int exponentOfTerm = Integer.parseInt(this.terms.get(e).split("\\^")[1]);
				this.exponents.add(exponentOfTerm);
			}
			else if (this.terms.get(e).contains(variable) && !this.terms.get(e).contains("^"))
			{
				this.exponents.add(1);
			}
			else if (!this.terms.get(e).contains("^") && !this.terms.get(e).contains(variable))
			{
				this.exponents.add(0);
			}
		}
	}

	private void extractCoefficients(String variable)
	{
		//cycle through each term and separate out each coefficient. This does NOT include non-polynomial functions
		for (int i = 0; i<=this.terms.size()-1; i++)
		{
			//System.out.println(this.terms.get(i).equals(variable));
			System.out.println(this.terms);
			//if there is a single positive variable term in the expression
			if (this.terms.get(i).contains("-"+variable) && this.terms.size() == 1)
			{
				System.out.println("Condition 1");
				this.coefficients.add(1.0);
			}
			//if there is a single negative variable term in the expression
			else if (this.terms.get(i).contains(variable) && this.terms.get(i).length() == 1)
			{
				System.out.println("Condition 2");
				this.coefficients.add(-1.0);
			}
			else if (this.terms.get(i).split(variable)[0].equals("") || this.terms.get(i).split(variable)[0].equals("+"))
			{
				System.out.println("Condition 3");
				this.coefficients.add(1.0);
			}
			else if (this.terms.get(i).split(variable)[0].equals("-"))
			{
				System.out.println("Condition 4");
				this.coefficients.add(-1.0);
			}
			else if (!this.terms.get(i).contains(variable) && String.valueOf(this.terms.get(i).charAt(0)).equals("") || this.terms.get(i).contains("+"))
			{
				System.out.println("Condition 5");
				this.coefficients.add(Double.parseDouble(this.terms.get(i).split(variable)[0]));
			}
			else if (!this.terms.get(i).contains(variable) && String.valueOf(this.terms.get(i).charAt(0)).equals("-"))
			{
				System.out.println("Condition 6");
				this.coefficients.add(Double.parseDouble(this.terms.get(i)));
			}
			else
			{
				System.out.println("Condition 7");
				this.coefficients.add(Double.parseDouble(this.terms.get(i).split(variable)[0]));
			}
		}
		System.out.println(this.getCoefficients());
	}

	private void determineDegree()
	{
		this.degree = this.exponents.get(0);
		for (int m = 1; m < this.exponents.size(); m++)
		{
			if (this.exponents.get(m) > this.degree)
			{
				this.degree = this.exponents.get(m);
			}
		}
	}

	public ArrayList<Double> getCoefficients()
	{
		return this.coefficients;
	}

	public ArrayList<String> getTerms()
	{
		return this.terms;
	}

	public ArrayList<Integer> getExponents()
	{
		return this.exponents;
	}

	public int getDegree()
	{
		return this.degree;
	}

	/*method so far only accounts for strictly algebraic calculations
	later revisions will account for mathematical functions included in the polynomial
	especially for logarithmic, trigonometric or other kinds of functions with multiple terms
	*/
	public double reconstructAndEvaluate(double inputtedValue)
	{
		double result = 0.0;
		for (int i = 0; i < this.terms.size(); i++)
		{
			double coefficient = this.coefficients.get(i);
			double exponent = (double)(this.exponents.get(i));
			if (this.terms.size() == 1)
			{
				result = coefficient*Math.pow(inputtedValue,exponent);
			}
			else
			{
				result = result + coefficient*Math.pow(inputtedValue,exponent);
			}
		}
		return result;
	}

	public int synDivide(String factor, Polynomial p)
	{
		ArrayList<Integer> factors = new ArrayList<Integer>();
		return 0;
		
	}

	private void generateTable(int lowerLimit, int upperLimit)
	{
		ArrayList<Double> xValues = new ArrayList<Double>();
		ArrayList<Double> yValues = new ArrayList<Double>();
		double newTabularValue = lowerLimit;
		while (newTabularValue <= upperLimit)
		{
			xValues.add(newTabularValue);
			yValues.add(this.reconstructAndEvaluate(newTabularValue));
			newTabularValue++;
		}
		
		System.out.println("f(x) = " + this.expression);
		System.out.println("x" + "\t\t\t" + "y");
		System.out.println("-------------------------------");
		for (int i = 0; i <= xValues.size()-1;i++)
		{
			System.out.println(xValues.get(i) + "\t\t\t" + yValues.get(i));
		}
	}

	private boolean isLinear()
	{
		if (this.degree == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isQuadratic()
	{
		if (this.degree == 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isCubic()
	{
		if (this.degree == 3)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isQuartic()
	{
		if (this.degree == 4)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isQuintic()
	{
		if (this.degree == 5)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void constructPolynomial(ArrayList<Double> coefficients, ArrayList<Integer> exponents, String variable)
	{
		
	}

	public static void main(String[] args)
	{
		Polynomial_v7 p = new Polynomial_v7("x");
		System.out.println(p.getTerms());
		System.out.println(p.getCoefficients());
		System.out.println(p.getExponents());
		p.generateTable(1,10);
	}
}