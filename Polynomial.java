import java.util.*;
import java.lang.Math.*;

public class Polynomial
{
	private ArrayList<String> terms = new ArrayList<String>();
	private ArrayList<String> coefficients = new ArrayList<String>();

	//set at zero until constructor will set it to a value using the getDegree() method
	private int degree = 0;
	private String[] types = {"constant","linear","quadratic","cubic","quartic","quintic"};
	private String kindOfPolynomial = null;
	private String[] functionPrefixes = {"sin(", "cos(", "tan(", "ln(", "log(", "exp(", "asin(", "acos(", "atan", "sqrt("};

	Scanner keyboard = new Scanner(System.in);

	public Polynomial()
	{
		//throw exception that if any function prefixes are contained in the given "polynomial" then Java should state this as such and have the user to try again
		System.out.println("Enter your polynomial.");
		System.out.println("Remember to use asterisk symbols for multiplication and forward slashes for division.");
		System.out.println("Be sure to use parentheses whenever you can to clarify the operation as much as possible.");

		String pText = keyboard.next();
		this.deconstructPolynomial(pText);
	}

	private void deconstructPolynomial(String characters)
	{
		int currentSplitIndex = 0;
		int previousSplitIndex = 0;
		String result = null;
		String lastTerm = null;

		//identify and separate each of the terms as each element in the ArrayList
		int i = 0;
		int tempLocation = 0;
		while (i < characters.length())
		{
			if (characters.charAt(i) == '+' || characters.charAt(i) == '-')
			{
				StringBuilder newSubstring = new StringBuilder();
				for (int j = previousSplitIndex; j <= i-1; j++)
				{
					newSubstring.append(characters.charAt(j));
					result = newSubstring.toString();
				}
			this.terms.add(result);
			tempLocation = i;
			previousSplitIndex = tempLocation;
			}
			else
			{
				StringBuilder builder = new StringBuilder();
				for (int k = previousSplitIndex; k < characters.length(); k++)
				{
					builder.append(characters.charAt(k));
					lastTerm = builder.toString();
				}
			}
			previousSplitIndex = tempLocation;
			i++;
		}
		this.terms.add(lastTerm);
	}

	/*private double reconstructAndEvaluate()
	{
		double result = 0;
		for (int i = 0; i <= p.size()-1; i++)
		{
			if (this.terms.get(i))
			for (int j = 0; j <= this.functionPrefixes.length-1; j++)
			{
				if (this.terms.get(i).contains(j))
			}
		}
	}*/

	//this method will just return the value at x.  User does not need to see inner workings of the private helper method
	public double evaluate()
	{
		return 0;
	}

	//place logic in here to determine the degree of the polynomial
	private void setDegree()
	{
		this.degree = 0;
	}

	private String getDegree()
	{
		return null;
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

	/*private ArrayList<Double> extractCoefficients()
	{
		for (int i = 0; i <= this.coefficients.size()-1;i++)
		{

		}
	}*/

	public static void main(String[] args)
	{
		Polynomial p = new Polynomial();
		System.out.println(p.terms);
	}
}