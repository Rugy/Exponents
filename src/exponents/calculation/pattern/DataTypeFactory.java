package exponents.calculation.pattern;

/**
 * @author Andreas Heimann </br>
 * 
 *         Abstract class that's extended by {@link DataTypeBigInteger} and
 *         {@link DataTypeDouble}
 *
 */
public abstract class DataTypeFactory {

	public enum CalculationMethod {

		ADDITION, MULTIPLICATION;

	}

	public abstract CalculationBigInteger getBigIntegerCalculation(
			CalculationMethod calculationMethod, int exponent);

	public abstract CalculationDouble getDoubleCalculation(
			CalculationMethod calculationMethod, int exponent);

}
