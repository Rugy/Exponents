package exponents.calculation.pattern;

import exponents.calculation.implementation.DoubleAddition;
import exponents.calculation.implementation.DoubleMultiplication;

/**
 * @author Andreas Heimann </br>
 * 
 *         Returns Double calculation using either addition or multiplication,
 *         based on {@link CalculationMethod}.
 *
 */
public class DataTypeDouble extends DataTypeFactory {

	@Override
	public CalculationBigInteger getBigIntegerCalculation(
			CalculationMethod calculationMethod, int exponent) {
		throw new IllegalArgumentException(
				"Should not use CreateDouble to access BigIntegers.");
	}

	@Override
	public CalculationDouble getDoubleCalculation(
			CalculationMethod calculationMethod, int exponent) {
		if (calculationMethod == null) {
			throw new IllegalArgumentException(
					"no Argument when requesting CreateDouble.");
		}

		if (calculationMethod == CalculationMethod.ADDITION) {
			return new DoubleAddition(exponent);
		} else if (calculationMethod == CalculationMethod.MULTIPLICATION) {
			CalculationDouble doubleMultiplication = DoubleMultiplication
					.getMultiplicationMethod(exponent);
			doubleMultiplication.setExponent(exponent);
			return doubleMultiplication;
		}

		return null;
	}
}
