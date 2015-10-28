package calculation;

public abstract class CalculationDataType {

	public enum DataType {

		BIGINTEGER, DOUBLE;

	}

	public abstract CalculationBigInteger getBigIntegerCalculation(
			DataType bigInteger);

	public abstract CalculationDouble getDoubleCalculation(DataType doubleCalc);

}
