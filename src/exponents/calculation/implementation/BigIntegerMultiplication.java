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
public class BigIntegerMultiplication implements CalculationBigInteger {

	private static BigIntegerMultiplication multiplicator = new BigIntegerMultiplication();
	private int base;
	private int exponent = 1;
	private BigInteger result;

	// Private constructor so no new Instances can be generated
	private BigIntegerMultiplication() {
	}

	public int getBase() {
		return base;
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

	public static BigIntegerMultiplication getMultiplicationMethod(int exponent) {
		return multiplicator;
	}

	public BigInteger getResult() {
		return result;
	}

	@Override
	public BigInteger calculate(int base) {
		if (base <= 1) {
			throw new IllegalArgumentException();
		}

		this.base = base;
		result = BigInteger.valueOf(base).pow(exponent);
		return result;
	}
}
