package calculation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author Andreas Heimann
 * 
 *         Used to create the <code>additionStack</code> that is required for
 *         additionMethods as an array of either <code>Double</code> or
 *         <code>BigInteger</code>.
 * 
 */
public class StackCreatorImpl implements StackCreator {

	/**
	 * {@inheritDoc}
	 */
	public BigInteger[] createBigIntegerStack(int base, int exponent)
			throws IllegalArgumentException {
		if (base <= 0 || exponent <= 0) {
			throw new IllegalArgumentException(
					"base and exponent must be positive integers.");
		}

		BigInteger[] baseMultiplications = new BigInteger[exponent];
		ArrayList<BigInteger[]> subtractionLevels = new ArrayList<>();
		BigInteger[] additionStack = new BigInteger[exponent];

		// create top layer, needed for calculation of lower ones
		for (int i = 0; i < baseMultiplications.length; i++) {
			baseMultiplications[i] = BigInteger.valueOf(i + base);
			for (int j = 1; j < exponent; j++) {
				baseMultiplications[i] = baseMultiplications[i]
						.add(multiplyBigInt(i + base - 1,
								baseMultiplications[i]));
			}
		}

		// fill all other layers, needed to find latest value of each stack
		for (int i = 0; i < exponent - 1; i++) {
			BigInteger[] levelStack = new BigInteger[exponent - 1 - i];
			if (subtractionLevels.size() == 0) {
				for (int j = 0; j < levelStack.length; j++) {
					levelStack[j] = baseMultiplications[j + 1]
							.subtract(baseMultiplications[j]);
				}
			} else {
				for (int j = 0; j < levelStack.length; j++) {
					levelStack[j] = subtractionLevels.get(i - 1)[j + 1]
							.subtract(subtractionLevels.get(i - 1)[j]);
				}
			}

			subtractionLevels.add(levelStack);
		}

		// add factorial of <code>exponent</code> as value in the last layer
		subtractionLevels.add(new BigInteger[] { BigInteger
				.valueOf((long) factorialRecursively(exponent)) });

		// fill additionStack
		additionStack[0] = baseMultiplications[baseMultiplications.length - 1];
		for (int i = 0; i < additionStack.length - 1; i++) {
			BigInteger[] array = subtractionLevels.get(i);
			additionStack[i + 1] = array[array.length - 1];
		}

		return additionStack;
	}

	/**
	 * {@inheritDoc}
	 */
	public double[] createDoubleStack(int base, int exponent)
			throws OverflowException, IllegalArgumentException {
		if (base <= 0 || exponent <= 0) {
			throw new IllegalArgumentException(
					"base and exponent must be positive integers.");
		}

		double[] baseMultiplications = new double[exponent];
		ArrayList<double[]> subtractionLevels = new ArrayList<>();
		double[] additionStack = new double[exponent];

		// create top layer, needed for calculation of lower ones
		for (int i = 0; i < baseMultiplications.length; i++) {
			baseMultiplications[i] = (double) (i + base);
			for (int j = 1; j < exponent; j++) {
				baseMultiplications[i] = multiplyDouble(i + base,
						baseMultiplications[i]);
			}
		}

		// fill all other layers, needed to find latest value of each stack
		for (int i = 0; i < exponent - 1; i++) {
			double[] levelStack = new double[exponent - 1 - i];
			if (subtractionLevels.size() == 0) {
				for (int j = 0; j < levelStack.length; j++) {
					levelStack[j] = baseMultiplications[j + 1]
							- baseMultiplications[j];
				}
			} else {
				for (int j = 0; j < levelStack.length; j++) {
					levelStack[j] = subtractionLevels.get(i - 1)[j + 1]
							- subtractionLevels.get(i - 1)[j];
				}
			}

			subtractionLevels.add(levelStack);
		}

		// add factorial of <code>exponent</code> as value in the last layer
		subtractionLevels.add(new double[] { factorialRecursively(exponent) });

		// fill additionStack
		additionStack[0] = baseMultiplications[baseMultiplications.length - 1];
		for (int i = 0; i < additionStack.length - 1; i++) {
			double[] array = subtractionLevels.get(i);
			additionStack[i + 1] = array[array.length - 1];
		}

		return additionStack;
	}

	/**
	 * {@inheritDoc}
	 */
	public BigInteger doubleToBigInteger(double number) {
		return new BigDecimal(number).toBigInteger();
	}

	/**
	 * {@inheritDoc}
	 */
	public double factorialRecursively(int exponent) {
		if (exponent > 12) {
			return factorialIteratively(exponent);
		}

		if (exponent < 0) {
			throw new ArithmeticException("calculating negative factorial");
		}

		if (exponent <= 1) {
			return 1;
		}

		double factorial = 0;

		// TODO THREADING
		for (int i = 1; i <= exponent; i++) {
			factorial += factorialRecursively(exponent - 1);
		}

		return factorial;
	}

	/**
	 * {@inheritDoc}
	 */
	public double factorialIteratively(int exponent) {
		if (exponent < 0) {
			throw new ArithmeticException("calculating negative factorial");
		}

		if (exponent <= 1) {
			return 1;
		}

		double factorial = 1;

		for (int i = 0; i < exponent; i++) {
			double factor = factorial;

			for (int j = 0; j < i; j++) {
				factorial += factor;
			}
		}

		return factorial;
	}

	/**
	 * {@inheritDoc}
	 */
	public BigInteger factorialBigIngeter(int exponent) {
		if (exponent < 0) {
			throw new ArithmeticException("calculating negative factorial");
		}

		if (exponent <= 1) {
			return BigInteger.valueOf(1);
		}

		BigInteger factorial = BigInteger.valueOf(1);

		for (int i = 0; i < exponent; i++) {
			BigInteger factor = factorial;

			for (int j = 0; j < i; j++) {
				factorial.add(factor);
			}
		}

		return factorial;
	}

	public double multiplyDouble(double factor, double number)
			throws OverflowException {
		double result = 0;
		int negativesCount = 0;

		if (factor < 0) {
			negativesCount++;
			factor = Math.abs(factor);
		}

		if (number < 0) {
			negativesCount++;
			number = Math.abs(number);
		}

		for (int i = 0; i < factor; i++) {
			result += number;
		}

		if (result == Double.POSITIVE_INFINITY) {
			throw new OverflowException("double overflow after multiplication");
		}

		if (negativesCount % 2 == 1) {
			double tmpResult = result;

			for (int i = 0; i < 2; i++) {
				result -= tmpResult;
			}
		}

		return result;
	}

	public BigInteger multiplyBigInt(double factor, BigInteger number) {
		BigInteger result = BigInteger.valueOf(0);
		int negativesCount = 0;

		if (factor < 0) {
			negativesCount++;
			factor = Math.abs(factor);
		}

		if (number.compareTo(BigInteger.valueOf(0)) < 0) {
			negativesCount++;
			number = number.negate();
		}

		for (int i = 0; i < factor; i++) {
			result = result.add(number);
		}

		if (negativesCount % 2 == 1) {
			result = result.negate();
		}

		return result;
	}

}
