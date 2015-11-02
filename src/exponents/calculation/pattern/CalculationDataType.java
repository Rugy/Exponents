package exponents.calculation.pattern;


public abstract class CalculationDataType {

	public enum CalculationMethod {

		ADDITION, MULTIPLICATION;

	}

	public abstract CalculationBigInteger getBigIntegerCalculation(
			CalculationMethod calculationMethod);

	public abstract CalculationDouble getDoubleCalculation(
			CalculationMethod calculationMethod);

}
