import java.util.*;
import java.lang.Math.*;

public class Linear //extends Polynomial
{
	private double independent_var = 0.0;
	private double constant_term = 0.0;
	private double dependent_var = 0.0;
	private double rate_of_change = 0.0;
	public Linear(double m,double x, double b)
	{
		this.rate_of_change = m;
		this.independent_var = x;
		this.constant_term = b;
		this.dependent_var = this.independent_var*this.rate_of_change + this.constant_term;
	}

	public double twoPointSlope(double x1, double y1, double x2, double y2)
	{
		double slope = 0.0;
		try
		{
			if (x2 - x1 == 0)
			{
				throw new jAlgException("Divide by zero error.");
			}
			else
			{
				slope = (y2 - y1)/(x2 - x1);
			}
		}
		//what should the program do if the line is vertical?
		catch(jAlgException ex)
		{
			System.out.println("Line is vertical.  Slope undefined at points: (" + x1 + "," + y1 + ") and (" + x2 + "," + y2 + ").");
			System.out.println("Pick another set of points.");
		}
		return slope;
	}

	public void stateEquation()
	{
		System.out.println("y = " + this.independent_var + "x + " + this.constant_term);
	}

	//this could be instead placed inside Polynomial class to be more widely used
	public void generateTable(int lowerLimit, int upperLimit)
	{
		ArrayList<Double> xValues = new ArrayList<Double>();
		ArrayList<Double> yValues = new ArrayList<Double>();
		double newTabularValue = lowerLimit;
		while (newTabularValue <= upperLimit)
		{
			xValues.add(newTabularValue);
			this.dependent_var = this.rate_of_change*newTabularValue+this.constant_term;
			yValues.add(this.dependent_var);

			newTabularValue++;
		}
		tableOfValues.add(xValues);
		tableOfValues.add(yValues);

		System.out.println("x" + "\t\t\t" + "y");
		System.out.println("-------------------------------");
		for (int i = 0; i <= xValues.size()-1;i++)
		{
			System.out.println(xValues.get(i) + "\t\t\t" + yValues.get(i));
		}
	}

	public double xIntercept()
	{
		return (this.dependent_var - this.constant_term)/this.rate_of_change;
	}

	//this can be also placed in Polynomial to be more widely used
	public double yIntercept()
	{
		return this.rate_of_change*0 + this.constant_term;
	}

	public static void main(String[] args)
	{
		Linear line = new Linear(8.25, 1.75, 6.45);
		line.stateEquation();
		line.generateTable(5,100);
	}
}