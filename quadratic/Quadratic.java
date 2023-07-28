import java.util.*;
import java.lang.Math.*;

public class Quadratic extends Polynomial
{
	private ArrayList<ComplexNumber> imaginaryRoots = new ArrayList<ComplexNumber>(); 
	private ArrayList<Double> realRoots = new ArrayList<Double>();
	private double discriminant = 0.0;

	public void determinant(doube a, double b, double c)
	{
		this.determinant = b*b - 4*a*c;
	}

	public void solve(double a, double b, double c)
	{
		/*For the quadratic to have an imaginary root, the discriminant must be less than zero.
		if it less than zero, this means the root needs to be broken down as such:
		sqrt((-1)*value) = sqrt(-1)*sqrt(value)
		sqrt(-1) = i

		The whole solution will become a complex number, with -b/2a being the real component, and
		the result of sqrt(b^2-4ac)/2a becomes the imaginary component
		*/
		if this.determinant < 0
		{
			ComplexNumber complexRoot = new ComplexNumber((b*b)/2*a,(-1)*Math.abs((b*b)-4*a*c));
			ComplexNumber complexConjugate = new ComplexRoot((b*b)/2*a,Math.abs((b*b)-4*a*c));
			this.imaginaryRoots.add(complexRoot);
			this.imagrinarRoots.add(complexConjugate);
		}
		else if this.discriminant > 0
		{	
			this.roots.add(((-1)*b + Math.sqrt(this.determinant))/2*a);
			this.roots.add(((-1)*b - Math.sqrt(this.determinant))/2*a);
		}
		else if this.discriminant == 0
		{
			this.roots.add(((-1)*b + Math.sqrt(this.determinant))/2*a);
		}
	}
}