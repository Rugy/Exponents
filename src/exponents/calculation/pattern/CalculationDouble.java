package exponents.calculation.pattern;

import exponents.calculation.implementation.OverflowException;

public interface CalculationDouble {

	public double calculate(int base) throws OverflowException;

	public int getExponent();

	public void setExponent(int exponent);

}
