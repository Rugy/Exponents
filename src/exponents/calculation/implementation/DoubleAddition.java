package exponents.calculation.implementation;

import exponents.calculation.pattern.CalculationDouble;

/**
 * @author Andreas Heimann </br>
 * 
 *         Uses addition to calculate exponents. Reuse by incrementing
 *         <code>base</code> of {@link #calculate(int)} method
 *
 */
public class DoubleAddition implements CalculationDouble {

	private int exponent;
	private int currentBase;
	private double exponentFactorial;
	private double[] additionStack;
	private StackCreator stackCreator;
	private double result;

	/**
	 * Constructor used for exponential calculation. Needs <code>exponent</code>
	 * as a parameter for construction.
	 * 
	 * @param exponent
	 *            to be used for the calculations.
	 */
	public DoubleAddition(int exponent) {
		if (exponent <= 1) {
			throw new IllegalArgumentException("Exponent can't be 1 or lower.");
		}

		stackCreator = new StackCreatorImpl();
		exponentFactorial = stackCreator.factorialIteratively(exponent);
		this.exponent = exponent;
	}

	public DoubleAddition(int exponent, StackCreator stackCreator) {
		if (exponent <= 1) {
			throw new IllegalArgumentException("Exponent can't be 1 or lower.");
		}

		this.stackCreator = stackCreator;
		exponentFactorial = stackCreator.factorialIteratively(exponent);
		this.exponent = exponent;
	}

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		exponentFactorial = stackCreator.factorialIteratively(exponent);
		additionStack = null;
		this.exponent = exponent;
	}

	public int getBase() {
		return currentBase;
	}

	public double getExponentFactorial() {
		return exponentFactorial;
	}

	public double[] getAdditionStack() {
		return additionStack;
	}

	public void setAdditionStack(double[] additionStack) {
		this.additionStack = additionStack;
	}

	public double getResult() {
		return result;
	}

	/**
	 * Calculates the <code>base</code> with the <code>exponent</code> only
	 * through means of addition. Iterative calls with incrementing values allow
	 * to skip all regular exponential calculations in favor of number of
	 * <code>exponent</code>s additions.
	 * 
	 * @param base
	 *            value for the calculation.
	 * @return <code>base</code> to the power of {@link #exponent}
	 * @throws OverflowException
	 *             if calculation exceeds {@link Double#MAX_VALUE}
	 * @throws IllegalArgumentException
	 *             if base is equal or less than 1
	 */
	@Override
	public double calculate(int base) throws OverflowException {
		if (base <= 1) {
			throw new IllegalArgumentException();
		}

		double result = 0;

		// regular exponential calculation if additionStack can't be created
		if (base < exponent) {
			result = base;

			for (int i = 1; i < exponent; i++) {
				result = stackCreator.multiplyDouble(base, result);
			}

			// additionStacking if previous calculated base is incremented
		} else if (base == currentBase + 1 && null != additionStack) {
			for (int i = additionStack.length - 1; i >= 0; i--) {
				if (i == additionStack.length - 1) {
					additionStack[i] += exponentFactorial;
				} else {
					additionStack[i] += additionStack[i + 1];
				}
			}
			result = additionStack[0];

			// creating the additionStack if result can't be extrapolated from
			// the previous stack or there is no stack
		} else {
			additionStack = stackCreator.createDoubleStack(base - exponent + 1,
					exponent);
			result = additionStack[0];
		}

		currentBase = base;
		this.result = result;
		return result;
	}
}
