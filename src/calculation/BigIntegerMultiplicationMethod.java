package calculation;

import java.math.BigInteger;

public class BigIntegerMultiplicationMethod implements CalculationBigInteger {

	private static BigIntegerMultiplicationMethod multiplicator = new BigIntegerMultiplicationMethod();

	private BigIntegerMultiplicationMethod() {

	}

	public static BigIntegerMultiplicationMethod getMultiplicationMethod() {
		return multiplicator;
	}

	@Override
	public BigInteger calculate(int base, int exponent) {
		return BigInteger.valueOf(base).pow(exponent);
	}

}
