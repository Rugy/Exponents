package exponents.calculation.pattern;

public class FactoryProducer {

	public enum DataType {

		BIGINTEGER, DOUBLE;

	}

	public static CalculationDataType getCalculationDataType(DataType datatype) {
		if (datatype == null) {
			throw new IllegalArgumentException(
					"No Datatype selected when requesting CalculationDatatype.");
		}

		if (datatype == DataType.BIGINTEGER) {
			return new CreateBigIntegerCalculation();
		}

		if (datatype == DataType.DOUBLE) {
			return new CreateDoubleCalculation();
		}

		return null;
	}

}
