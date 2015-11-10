package exponents.calculation.pattern;

/**
 * @author Andreas Heimann </br>
 * 
 *         Gives access to {@link DataTypeFactory} and returns either
 *         {@link DataTypeDouble} or {@link DataTypeBigInteger} based on
 *         requested {@link DataType}-Enumeration.
 *
 */
public class FactoryProducer {

	public enum DataType {

		BIGINTEGER, DOUBLE;

	}

	public static DataTypeFactory getCalculationDataType(DataType datatype) {
		if (datatype == null) {
			throw new IllegalArgumentException(
					"No Datatype selected when requesting CalculationDatatype.");
		}

		if (datatype == DataType.BIGINTEGER) {
			return new DataTypeBigInteger();
		}

		if (datatype == DataType.DOUBLE) {
			return new DataTypeDouble();
		}

		return null;
	}

}
