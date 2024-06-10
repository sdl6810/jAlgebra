import java.lang.Math.*;
import java.util.*;

public class Equation
{
	/*
	inequalities[0] represents <
	inequalities[1] represents <=
	inequalities[2] represents >
	inequalities[3] represents >=
	*/

	public Object lhs;
	public Object rhs;

	private int[] inequalities = [0,0,0,0];
	private boolean isCompoundInequality = false;
	public Equation(Object lhs, Object rhs, boolean inequality, boolean absolutevalue)
	{
		double d_l, d_r;
		Polynomial p_l, p_r;
		RationExpression r_l, r_r;
		Radical rad_l, rad_r;

		if (obj instanceOf Double)
			d_l = (double)lhs;
		else if (obj instanceof Polynomial)
			p_l = (Polynomial)lhs;
		else if (obj instanceof RationalExpression)
			r_l = (RationalExpression)lhs;
		else if (obj instanceof Radical)
			rad_l = (Radical)rad;

		if (obj instanceOf Double)
			d_r = (double)rhs;
		else if (obj instanceof Polynomial)
			p_r = (Polynomial)rhs;
		else if (obj instanceof RationalExpression)
			r_r = (RationalExpression)rhs;
		else if (obj instanceof Radical)
			rad_r = (Radical)rhs;

		if (this.absolutevalue == true)
			this.isCompoundInequality = true;
	}

	public double solveSimpleInequality(Polynomial leftSide, Object rightSide, int status)
	{
		double d;
		Polynomial p;
		leftSide.sortInDescendingOrder();
		leftSide.simplify();
		ArrayList<Double> c = leftSide.getCoefficients();
		ArrayList<Double> e = leftSide.getExponents();
		int degree = leftSide.degree();

	}

	public static void main(String[] args)
	{

	}
}