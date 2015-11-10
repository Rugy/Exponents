package exponents.calculation.implementation;

import exponents.calculation.pattern.CalculationDouble;


/**
 * @author Andreas Heimann </br>
 * 
 *         Uses multiplication to calculate exponents. Singleton pattern, point
 *         with {@link #getMultiplicationMethod(int)} and initialize
 *         <code>exponent</code> with {@link #setExponent(int)}
 *
 */
public class DoubleMultiplication implements CalculationDouble {

	private static DoubleMultiplication multiplicator = new DoubleMultiplication();
	private int exponent = 1;

	// Private constructor so no new Instances can be generated
	private DoubleMultiplication() {
	}

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		if (exponent <= 1) {
			throw new IllegalArgumentException("Exponent can't be 1 or lower.");
		}

		this.exponent = exponent;
	}

	public static DoubleMultiplication getMultiplicationMethod(
			int exponent) {
		return multiplicator;
	}

	@Override
	public double calculate(int base) throws OverflowException {
		if (base <= 1) {
			throw new IllegalArgumentException();
		}

		double result = Math.pow(base, exponent);

		if (result == Double.POSITIVE_INFINITY) {
			throw new OverflowException("double overflow after multiplication");
		}

		return result;
	}

}