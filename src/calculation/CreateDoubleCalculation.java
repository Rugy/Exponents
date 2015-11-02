package calculation;

public class CreateDoubleCalculation extends CalculationDataType {

	@Override
	public CalculationBigInteger getBigIntegerCalculation(
			CalculationMethod calculationMethod) {
		throw new IllegalArgumentException(
				"Should not use CreateDouble to access BigIntegers.");
	}

	@Override
	public CalculationDouble getDoubleCalculation(
			CalculationMethod calculationMethod) {
		if (calculationMethod == null) {
			throw new IllegalArgumentException(
					"no Argument when requesting CreateDouble.");
		}

		if (calculationMethod == CalculationMethod.ADDITION) {
			return new DoubleAdditionMethod(1);
		} else if (calculationMethod == CalculationMethod.MULTIPLICATION) {
			return DoubleMultiplicationMethod.getMultiplicationMethod(1);
		}

		return null;
	}

}
