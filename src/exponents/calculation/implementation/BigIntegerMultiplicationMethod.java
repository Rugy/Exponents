package exponents.calculation.implementation;

import java.math.BigInteger;

import exponents.calculation.pattern.CalculationBigInteger;

/**
 * @author Andreas Heimann </br>
 * 
 *         Uses multiplication to calculate exponents. Singleton pattern, point
 *         with {@link #getMultiplicationMethod(int)} and initialize
 *         <code>exponent</code> with {@link #setExponent(int)}
 *
 */
public class BigIntegerMultiplicationMethod implements CalculationBigInteger {

	private static BigIntegerMultiplicationMethod multiplicator = new BigIntegerMultiplicationMethod();
	private int exponent = 1;

	// Private constructor so no new Instances can be generated
	private BigIntegerMultiplicationMethod() {
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

	public static BigIntegerMultiplicationMethod getMultiplicationMethod(
			int exponent) {
		return multiplicator;
	}

	@Override
	public BigInteger calculate(int base) {
		if (base <= 1) {
			throw new IllegalArgumentException();
		}

		return BigInteger.valueOf(base).pow(exponent);
	}

}
