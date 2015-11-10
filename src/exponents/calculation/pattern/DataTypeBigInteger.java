package exponents.calculation.pattern;

import exponents.calculation.implementation.BigIntegerAddition;
import exponents.calculation.implementation.BigIntegerMultiplication;

/**
 * @author Andreas Heimann </br>
 * 
 *         Returns BigInteger calculation using either addition or
 *         multiplication, based on {@link CalculationMethod}.
 *
 */
public class DataTypeBigInteger extends DataTypeFactory {

	@Override
	public CalculationBigInteger getBigIntegerCalculation(
			CalculationMethod calculationMethod, int exponent) {
		if (calculationMethod == null) {
			throw new IllegalArgumentException(
					"no Argument when requesting CreateBigInt.");
		}

		if (calculationMethod == CalculationMethod.ADDITION) {
			return new BigIntegerAddition(exponent);
		} else if (calculationMethod == CalculationMethod.MULTIPLICATION) {
			CalculationBigInteger bigMultiplication = BigIntegerMultiplication
					.getMultiplicationMethod(exponent);
			bigMultiplication.setExponent(exponent);
			return bigMultiplication;
		}

		return null;
	}

	@Override
	public CalculationDouble getDoubleCalculation(
			CalculationMethod calculationMethod, int exponent) {
		throw new IllegalArgumentException(
				"Should not use CreateBigInteger to access Doubles.");
	}
}
