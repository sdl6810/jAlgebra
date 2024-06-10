import java.util.*;
import java.lang.Math.*;

public class Calculus
{
	public Calculus()
	{

	}

	public static Polynomial d_dx(Polynomial f)
	{
		ArrayList<Double> df_dxCoeffs = new ArrayList<Double>();
		ArrayList<Double> df_dxExpons = new ArrayList<Double>();
		for (int i = 0; i < f._exponents.size(); i++)
		{
			df_dxCoeffs.add(f._exponents.get(i)*f._coefficients.get(i));
			df_dxExpons.add(f._exponents.get(i)-1);	
			if (f._coefficients.get(i) == 0.0)
			{
				f._coefficients.remove(i);
				f._exponents.remove(i);
			}
		}
		Polynomial df_dx = new Polynomial(f._variable,df_dxCoeffs,df_dxExpons);

		return df_dx;
	}

	public static Polynomial nthOrder_d_dx(Polynomial p, int order)
	{
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		Polynomial nthOrder = Calculus.d_dx(p);
		if (order == 1)
			return nthOrder;
		else
		{
			for (int i = 2; i <= order; i++)
				nthOrder = Calculus.d_dx(nthOrder);	
		}
		
		return nthOrder;
	}

	//d/dx(f*g) = f(x)*g'(x) + f'(x)*g(x)
	public static Polynomial d_dxProduct(Polynomial f, Polynomial g)
	{
		Polynomial df_dx = Calculus.d_dx(f);
		Polynomial dg_dx = Calculus.d_dx(g);
		
		//f(x)*g'(x)
		Polynomial f_dg_dx = Polynomial.doubleDistribute(f,dg_dx);

		//f'(x)*g(x)
		Polynomial g_df_dx = Polynomial.doubleDistribute(g,df_dx);

		//add them together
		f_dg_dx.add(g_df_dx);

		return f_dg_dx;
	}

	//d/dx(f/g) = (f'(x)*g(x) - f(x)*g'(x))/[g(x)]^2
	public static RationalExpression d_dxQuotient(Polynomial f, Polynomial g)
	{
		Polynomial df_dx = Calculus.d_dx(f);
		Polynomial dg_dx = Calculus.d_dx(g);

		//f'(x)*g(x)
		Polynomial df_dx_g = Polynomial.doubleDistribute(df_dx,g);

		//f(x)*g'(x)
		Polynomial dg_dx_f = Polynomial.doubleDistribute(dg_dx,f);

		//negate the second product and combine it with the first one
		dg_dx_f.negate();
		df_dx_g.add(dg_dx_f);

		//divide by g(x)^2
		Polynomial denominator = Polynomial.power(g,2);

		ArrayList<Polynomial> n = new ArrayList<Polynomial>();
		ArrayList<Polynomial> d = new ArrayList<Polynomial>();
		n.add(df_dx_g);
		d.add(denominator);

		RationalExpression r = new RationalExpression(n,d);
		return r;
	}

	//develop code for finding the derivative of a nested function
	public static Polynomial powerRule(Polynomial p)
	{
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		Polynomial q = p;
		q.distribute(p.higherPower,0.0,p);
		q.higherPower = p.higherPower-1;

		return q;
	}

	public static ArrayList<Polynomial> chainRule(Polynomial inner, Polynomial outer)
	{
		ArrayList<Polynomial> chain = new ArrayList<Polynomial>();
		Polynomial dy_du = Calculus.d_dx(inner);
		System.out.println(dy_du.printPolynomial());
		Polynomial du_dx = Calculus.d_dx(outer);
		System.out.println(du_dx.printPolynomial());

		chain.add(dy_du);
		chain.add(du_dx);

		return chain;
	}

	public static Polynomial chainRuleDistribute(ArrayList<Polynomial> dy_du_dx)
	{
		ArrayList<Double> emptyC = new ArrayList<Double>();
		ArrayList<Double> emptyE = new ArrayList<Double>();
		Polynomial chainResult = new Polynomial(dy_du_dx.get(0)._variable,emptyC,emptyE);
		Polynomial aux = new Polynomial(dy_du_dx.get(0)._variable,emptyC,emptyE);
		for (int i = 1; i < dy_du_dx.size(); i++)
		{
			aux = Polynomial.doubleDistribute(dy_du_dx.get(i),dy_du_dx.get(i-1));
			aux.simplify();
			chainResult.add(aux);
		}
		chainResult.simplify();

		return chainResult;
	}

	public static Polynomial indefiniteIntegral(Polynomial p)
	{
		ArrayList<Double> c = new ArrayList<Double>();
		ArrayList<Double> e = new ArrayList<Double>();
		Polynomial integrate = new Polynomial(p._variable,c,e);

		for (int i = 0; i < p._exponents.size(); i++)
		{
			integrate._coefficients.add(p._coefficients.get(i)/(p._exponents.get(i)+1));
			integrate._exponents.add(p._exponents.get(i)+1);
		}
		return integrate;
	}

	public static double definiteIntegral(Polynomial p, double upper, double lower)
	{
		Polynomial integrated = indefiniteIntegral(p);
		double difference = integrated.evaluate(upper) - integrated.evaluate(lower);
		return difference;
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

		Polynomial dp_dx = Calculus.d_dx(p);
		Polynomial dq_dx = Calculus.d_dx(q);

		ArrayList<Polynomial> dy_dx = Calculus.chainRule(p,t);
		Polynomial dy_du_dx = Calculus.chainRuleDistribute(dy_dx);
		System.out.println(dy_dx.get(0).printPolynomial());
		System.out.println(dy_dx.get(1).printPolynomial());
		System.out.println(dy_du_dx.printPolynomial());
	}
}