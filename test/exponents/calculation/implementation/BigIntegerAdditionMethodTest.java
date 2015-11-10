package exponents.calculation.implementation;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BigIntegerAdditionMethodTest {

	BigIntegerAddition additionMethod;
	StackCreator mockedStackCreator;

	@Before
	public void setUp() {
		mockedStackCreator = mock(StackCreatorImpl.class);
	}

	// Verify correct results
	@Test
	public void testCalculateBaseFourWithExponentThreeEqualsSixtyFour()
			throws OverflowException {
		BigInteger[] testBigIntArray = { BigInteger.valueOf(64),
				BigInteger.valueOf(37), BigInteger.valueOf(18) };
		when(mockedStackCreator.factorialBigIngeter(3)).thenReturn(
				BigInteger.valueOf(6));
		when(mockedStackCreator.createBigIntegerStack(2, 3)).thenReturn(
				testBigIntArray);
		additionMethod = new BigIntegerAddition(3, mockedStackCreator);

		assertEquals("Calculation of Base 4 With Exponent 3 must 64",
				BigInteger.valueOf(64), additionMethod.calculate(4));
	}

	@Test
	public void testIncrementalCallBaseFourWithExponentThreeEqualsOneTwentyFive()
			throws OverflowException {
		BigInteger[] testBigIntArray = { BigInteger.valueOf(64),
				BigInteger.valueOf(37), BigInteger.valueOf(18) };
		when(mockedStackCreator.factorialBigIngeter(3)).thenReturn(
				BigInteger.valueOf(6));
		when(mockedStackCreator.createBigIntegerStack(2, 3)).thenReturn(
				testBigIntArray);
		additionMethod = new BigIntegerAddition(3, mockedStackCreator);

		additionMethod.calculate(4);
		assertEquals(
				"Incremental calculation of Base 5 With Exponent 3 must 125",
				BigInteger.valueOf(125), additionMethod.calculate(5));
	}

	@After
	public void tearDown() {
		additionMethod = null;
		mockedStackCreator = null;
	}
}
