package exponents;

import calculation.DoubleAdditionMethod;
import calculation.OverflowException;

public class Main {

	public static void main(String[] args) throws OverflowException {

		DoubleAdditionMethod test = new DoubleAdditionMethod(3);

		test.calculate(4);
	}
}
