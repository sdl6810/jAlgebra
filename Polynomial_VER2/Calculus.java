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
			if (f._coefficients.get(i) == 0.0 && f._exponents.get(i) < 0)
			{
				f._coefficients.remove(i);
				f._exponents.remove(i);
			}
			df_dxCoeffs.add(f._exponents.get(i)*f._coefficients.get(i));
			df_dxExpons.add(f._exponents.get(i)-1);	
			
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
		Polynomial denominator = Polynomial.doubleDistribute(g,g);

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
		Polynomial dy_du = new Polynomial(inner._variable, new ArrayList<Double>(), new ArrayList<Double>());
		Polynomial du_dx = new Polynomial(inner._variable, new ArrayList<Double>(), new ArrayList<Double>());
		ArrayList<Polynomial> chain = new ArrayList<Polynomial>();

		//outer(inner(x))
		if (inner._coefficients.size() == 0)
		{
			if (inner._exponents.size() == 0)
			{
				dy_du._coefficients.add(1.0);
				dy_du._exponents.add(1.0);
				dy_du.higherPower = 1.0;
			}
			else if (inner._exponents.size() > 0)
			{
				dy_du._coefficients.add(0.0);
			}

		}
		//example: (1x)^n, coefficient == 1
		else if (inner._coefficients.get(0) >= 1)
		{
			//(1x)^0 = 1
			if ((inner._exponents.get(0) == 0) || (inner._exponents.size() == 0))
			{
				dy_du._coefficients.add(0.0);
				dy_du._coefficients.add(0.0);
			}
			//(mx)^1 or (mx)^n where m >=1 and n >= 1
			else if (inner._exponents.get(0) >= 1)
			{
				//n*(1x)^n
				//d/dn ((1x)^n) = n*(1x)^(n-1)
				dy_du._coefficients.add(outer._exponents.get(0)*inner._exponents.get(0));
				dy_du._exponents.add(outer._exponents.get(0) - 1);
			}
		}

		du_dx = Calculus.d_dx(outer);

		chain.add(dy_du);
		chain.add(inner);

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

		c.add(1.0);
		e.add(3.0);

		c2.add(1.0);
		c2.add(1.0);
		e2.add(2.0);
		e2.add(0.0);

		Polynomial p = new Polynomial("x",c,e);
		Polynomial q = new Polynomial("x",c2,e2);
		Polynomial s = new Polynomial("x",c3,e3);
		Polynomial t = new Polynomial("x",c4,e4);

		Polynomial dp_dx = Calculus.d_dx(p);
		Polynomial dq_dx = Calculus.d_dx(q);

		System.out.println("p = " + p.printPolynomial());
		System.out.println("q = " + q.printPolynomial());
		ArrayList<Polynomial> dy_dx = Calculus.chainRule(q,p);

		Polynomial dy_du_dx = Calculus.chainRuleDistribute(dy_dx);
		for (int i = 0; i < dy_dx.size(); i++)
		{
			if (i == 3)
				System.out.println("");
			else
			{
				System.out.print("(");
				System.out.print(dy_dx.get(i).printPolynomial());
				System.out.print(")");
			}
		}
	}
}