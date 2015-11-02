package exponents;

import exponents.calculation.pattern.CalculationBigInteger;
import exponents.calculation.pattern.CalculationDataType;
import exponents.calculation.pattern.CalculationDataType.CalculationMethod;
import exponents.calculation.pattern.CalculationDouble;
import exponents.calculation.pattern.FactoryProducer;
import exponents.calculation.pattern.FactoryProducer.DataType;

public class Main {

	public static void main(String[] args) {

		CalculationDataType bigIntegerCalculator = FactoryProducer
				.getCalculationDataType(DataType.BIGINTEGER);
		CalculationDataType doubleCalculator = FactoryProducer
				.getCalculationDataType(DataType.DOUBLE);

		CalculationBigInteger bigAddition = bigIntegerCalculator
				.getBigIntegerCalculation(CalculationMethod.ADDITION);
		CalculationBigInteger bigMultiplication = bigIntegerCalculator
				.getBigIntegerCalculation(CalculationMethod.MULTIPLICATION);
		CalculationDouble doubleAddition = doubleCalculator
				.getDoubleCalculation(CalculationMethod.ADDITION);
		CalculationDouble doubleMultiplication = doubleCalculator
				.getDoubleCalculation(CalculationMethod.MULTIPLICATION);

	}
}
