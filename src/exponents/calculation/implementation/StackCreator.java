package exponents.calculation.implementation;

import java.math.BigInteger;

/**
 * @author Andreas Heimann
 * 
 *         Interface of {@link StackCreatorImpl}
 *
 */
public interface StackCreator {

	/**
	 * Creates an <code>additionStack</code> with <code>BigIntegers</code> for
	 * the given <code>exponent</code> . It contains the least amount of
	 * additions required to determine the outcome of the next number.
	 * 
	 * @param exponent
	 *            integer that determines the amount of stacks required to
	 *            calculate the next number. Must be greater than 1. Can not be
	 *            null.
	 * @param base
	 *            the integer the additionStack starts at.
	 * 
	 * @return <code>additionStack</code> with more than one entries if
	 *         <code>exponent</code> was greater than 1.<br>
	 *         empty <code>additionStack</code> if <code>exponent</code> is
	 *         smaller or equal to 1.
	 */
	public BigInteger[] createBigIntegerStack(int base, int exponent)
			throws IllegalArgumentException;

	/**
	 * Creates an <code>additionStack</code> with <code>Doubles</code> for the
	 * given <code>exponent</code> . It contains the least amount of additions
	 * required to determine the outcome of the next number.
	 * 
	 * @param exponent
	 *            integer that determines the amount of stacks required to
	 *            calculate the next number. Must be greater than 1. Can not be
	 *            null.
	 * @param base
	 *            the integer the additionStack starts at.
	 * 
	 * @return <code>additionStack</code> with more than one entries if
	 *         <code>exponent</code> was greater than 1.<br>
	 *         empty <code>additionStack</code> if <code>exponent</code> is
	 *         smaller or equal to 1.
	 */
	public double[] createDoubleStack(int base, int exponent)
			throws OverflowException, IllegalArgumentException;

	/**
	 * @param number
	 *            Double that is to be turned into BigInteger.
	 * @return BigInteger with <code>number</code> as value, the fraction is
	 *         discarded.
	 */
	public BigInteger doubleToBigInteger(double number);

	/**
	 * Recursively calculates the factorial of given number without using
	 * multiplication to create the value of the lowest stack-element.
	 * 
	 * @param exponent
	 *            - An Integer between 1 and 12.
	 * @return factorial of the <code>exponent</code> integer as a double if
	 *         it's between 1 and 12.
	 * 
	 */
	public double factorialRecursively(int exponent);

	/**
	 * Iteratively calculates the factorial of given number without using
	 * multiplication to create the value of the lowest stack-element.
	 * 
	 * @param exponent
	 *            - An Integer between 1 and Max.Integer.
	 * @return factorial of the <code>exponent</code> integer as a double if
	 *         it's between 1 and Max.Integer.
	 * 
	 */
	public double factorialIteratively(int exponent);

	/**
	 * Iteratively calculates the factorial of given number without using
	 * multiplication to create the value of the lowest stack-element.
	 * 
	 * @param exponent
	 *            - An Integer between 1 and Max.Integer.
	 * @return factorial of the <code>exponent</code> integer as a BigInteger
	 * 
	 */
	public BigInteger factorialBigIngeter(int exponent);

	public double multiplyDouble(double factor, double number)
			throws OverflowException;

	public BigInteger multiplyBigInt(double factor, BigInteger number);

}
