package exponents;

import java.util.Arrays;

import exponents.calculation.implementation.DoubleAddition;
import exponents.calculation.implementation.OverflowException;
import exponents.calculation.pattern.CalculationBigInteger;
import exponents.calculation.pattern.CalculationDouble;
import exponents.calculation.pattern.DataTypeFactory;
import exponents.calculation.pattern.DataTypeFactory.CalculationMethod;
import exponents.calculation.pattern.FactoryProducer;
import exponents.calculation.pattern.FactoryProducer.DataType;

public class Main {

	public static void main(String[] args) {

		int base = 20;
		int exponent = 4;

		DataTypeFactory bigIntegerCalculator = FactoryProducer
				.getCalculationDataType(DataType.BIGINTEGER);
		DataTypeFactory doubleCalculator = FactoryProducer
				.getCalculationDataType(DataType.DOUBLE);

		CalculationBigInteger bigAddition = bigIntegerCalculator
				.getBigIntegerCalculation(CalculationMethod.ADDITION, exponent);
		CalculationBigInteger bigMultiplication = bigIntegerCalculator
				.getBigIntegerCalculation(CalculationMethod.MULTIPLICATION,
						exponent);
		CalculationDouble doubleAddition = doubleCalculator
				.getDoubleCalculation(CalculationMethod.ADDITION, exponent);
		CalculationDouble doubleMultiplication = doubleCalculator
				.getDoubleCalculation(CalculationMethod.MULTIPLICATION,
						exponent);

		// Calculate every class once
		System.out.println(bigAddition.calculate(base));
		System.out.println(bigMultiplication.calculate(base));
		try {
			System.out.println(doubleAddition.calculate(base));
		} catch (OverflowException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(doubleMultiplication.calculate(base));
		} catch (OverflowException e) {
			e.printStackTrace();
		}

		// Look at additionStack of doubleAddition
		double[] additionStack = ((DoubleAddition) doubleAddition)
				.getAdditionStack();

		System.out.println(Arrays.toString(additionStack));
		System.out.println(((DoubleAddition) doubleAddition)
				.getExponentFactorial());

		// Increment doubleAddition by one
		try {
			doubleAddition.calculate(base + 1);
		} catch (OverflowException e) {
			e.printStackTrace();
		}

		// Look at additionStack of doubleAddition + 1, last number must be
		// increased by exponentFactorial
		additionStack = ((DoubleAddition) doubleAddition).getAdditionStack();

		System.out.println(Arrays.toString(additionStack));
		System.out.println(((DoubleAddition) doubleAddition)
				.getExponentFactorial());

	}
}
