import java.util.*;
import java.lang.Math.*;

public class Polynomial
{
	private ArrayList<String> terms = new ArrayList<String>();

	//set at zero until constructor will set it to a value using the getDegree() method
	private int degree = 0;
	private String[] types = ["constant","linear","quadratic","cubic","quartic","quintic"]
	private String kindOfPolynomial = null;
	private String[] functionPrefixes = ["sin(", "cos(", "tan(", "ln(", "log(", "e(", "asin(", "acos(", "atan", "sqrt("]

	public Polynomial()
	{

	}

	public ArrayList<String> breakIntoTerms(String characters)
	{
		ArrayList<String> substrings = new ArrayList<String>();

		int currentSplitIndex = 0;
		int previousSplitIndex = 0;
		String result = null;
		String lastTerm = null;

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
			substrings.add(result);
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
		substrings.add(lastTerm);
		return substrings;
	}

	public double identifyFunction(String functionInText)
	{

	}

	public double evaluate(ArrayList<Double> values)
	{
		
	}
}