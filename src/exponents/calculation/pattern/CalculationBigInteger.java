package exponents.calculation.pattern;

import java.math.BigInteger;

public interface CalculationBigInteger {

	public BigInteger calculate(int base);

	public int getBase();

	public int getExponent();

	public void setExponent(int exponent);

	public BigInteger getResult();

}
