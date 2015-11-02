package exponents.calculation.pattern;

import exponents.calculation.implementation.BigIntegerAdditionMethod;
import exponents.calculation.implementation.BigIntegerMultiplicationMethod;

public class CreateBigIntegerCalculation extends CalculationDataType {

	@Override
	public CalculationBigInteger getBigIntegerCalculation(
			CalculationMethod calculationMethod) {
		if (calculationMethod == null) {
			throw new IllegalArgumentException(
					"no Argument when requesting CreateBigInt.");
		}

		if (calculationMethod == CalculationMethod.ADDITION) {
			return new BigIntegerAdditionMethod(1);
		} else if (calculationMethod == CalculationMethod.MULTIPLICATION) {
			return BigIntegerMultiplicationMethod.getMultiplicationMethod(1);
		}

		return null;
	}

	@Override
	public CalculationDouble getDoubleCalculation(
			CalculationMethod calculationMethod) {
		throw new IllegalArgumentException(
				"Should not use CreateBigInteger to access Doubles.");
	}
}
