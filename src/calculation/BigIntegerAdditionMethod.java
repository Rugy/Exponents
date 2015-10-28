package calculation;

import java.math.BigInteger;

/**
 * @author Andreas Heimann
 *
 */
public class BigIntegerAdditionMethod implements CalculationBigInteger {

	private int exponent;
	private BigInteger[] additionStack;

	@Override
	public BigInteger calculate(int base, int exponent) {

		if (base <= exponent + 1) {
			return BigInteger.valueOf(0);
		}

		// Calculations
		if (exponent != this.exponent || additionStack == null) {
			// additionStack = createAdditionStack(exponent);
			this.exponent = exponent;
		}

		additionStack = calculateNumber();

		return additionStack[0];
	}

	public BigInteger[] calculateNumber() {

		BigInteger[] newAdditionStack = new BigInteger[additionStack.length];

		for (int i = newAdditionStack.length - 1; i >= 0; i--) {
			if (i == newAdditionStack.length - 1) {
				newAdditionStack[i] = additionStack[i];
			} else {
				newAdditionStack[i] = additionStack[i]
						.add(newAdditionStack[i + 1]);
			}
		}

		// System.out.println(Arrays.toString(newAdditionStack));

		return newAdditionStack;
	}
}
