# jAlgebra
Java packages I have created that can construct and evaluate algebraic polynomials and equations (WIP).

I was not happy to hear that Java does not have a package that can deal with equations, polynomials or anything much like Python does. So I thought I would attempt to start working on something like that and see how far I get.

The first class is the Polynomials class, which will encompass all algebraic equations and expressions.  The goal of this class, by the time it is done, is for it to be able to do the following:
> evaluate any polynomial given one or more inputs,
> solve any polynomial or at least come up with an approximate solution to a given degree of accuracy,
> factor out or expand one or more factored components of a polynomial

This class will have three other classes that will inherit its generic properites:
> linear functions
> quadratics
> cubics

Other equations will still be factored and expanded as expected, but will be approximately solved depending on the complexity and nature of the given polynomial.

The polynomial class, once finished, will be able to do the following as well:
> determine the degree of the given poynomial,
> sort the terms into descending order as expected,
> identify if the expression **actually is a polynomial**.  This means no fractional exponents of any kind.

Please review the following documentation below for each of the given methods developed to ensure the Polynomial class's functionality.
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
