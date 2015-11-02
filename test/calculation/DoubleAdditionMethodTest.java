package calculation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoubleAdditionMethodTest {

	DoubleAdditionMethod additionMethod;
	StackCreator mockedStackCreator;

	@Before
	public void setUp() {
		mockedStackCreator = mock(StackCreatorImpl.class);
	}

	// If-checks and stackCreator-calls
	@Test
	public void testCalculateWithBaseFourExponentSixCallsMultiplyFiveTimes()
			throws OverflowException {
		when(mockedStackCreator.factorialIteratively(6)).thenReturn(720d);
		when(mockedStackCreator.multiplyDouble(4, 4d)).thenReturn(16d);
		when(mockedStackCreator.multiplyDouble(4, 16d)).thenReturn(64d);
		when(mockedStackCreator.multiplyDouble(4, 64d)).thenReturn(256d);
		when(mockedStackCreator.multiplyDouble(4, 256d)).thenReturn(1024d);
		when(mockedStackCreator.multiplyDouble(4, 1024d)).thenReturn(4096d);
		additionMethod = new DoubleAdditionMethod(6, mockedStackCreator);

		additionMethod.calculate(4);
		verify(mockedStackCreator, times(5)).multiplyDouble(anyDouble(),
				anyDouble());
	}

	@Test
	public void testCalculateWithSameBaseAndExponentCallsMultiplicationZeroTimes()
			throws OverflowException {
		double[] testDoubleArray = { 3125.0, 2101.0, 1320.0, 750.0, 360.0 };
		when(mockedStackCreator.factorialIteratively(5)).thenReturn(120d);
		when(mockedStackCreator.createDoubleStack(1, 5)).thenReturn(
				testDoubleArray);
		additionMethod = new DoubleAdditionMethod(5, mockedStackCreator);

		additionMethod.calculate(5);
		verify(mockedStackCreator, never()).multiplyDouble(anyDouble(),
				anyDouble());
	}

	@Test
	public void testIncrementalCalculationsCallCreateDoubleStackOnlyOnce()
			throws OverflowException {
		double[] testDoubleArray = { 3125.0, 2101.0, 1320.0, 750.0, 360.0 };
		when(mockedStackCreator.factorialIteratively(5)).thenReturn(120d);
		when(mockedStackCreator.createDoubleStack(1, 5)).thenReturn(
				testDoubleArray);
		additionMethod = new DoubleAdditionMethod(5, mockedStackCreator);

		additionMethod.calculate(5);
		additionMethod.calculate(6);
		additionMethod.calculate(7);
		verify(mockedStackCreator, times(1)).createDoubleStack(anyInt(),
				anyInt());
	}

	// Verify correct results
	@Test
	public void testCalculateBaseFourWithExponentThreeEqualsSixtyFour()
			throws OverflowException {
		double[] testDoubleArray = { 64.0, 37.0, 18.0 };
		when(mockedStackCreator.factorialIteratively(3)).thenReturn(3d);
		when(mockedStackCreator.createDoubleStack(2, 3)).thenReturn(
				testDoubleArray);
		additionMethod = new DoubleAdditionMethod(3, mockedStackCreator);

		assertEquals("Calculation of Base 4 With Exponent 3 must 64", 64d,
				additionMethod.calculate(4), 0);
	}

	@Test
	public void testIncrementalCallBaseFourWithExponentThreeEqualsOneTwentyFive()
			throws OverflowException {
		double[] testDoubleArray = { 64.0, 37.0, 18.0 };
		when(mockedStackCreator.factorialIteratively(3)).thenReturn(6d);
		when(mockedStackCreator.createDoubleStack(2, 3)).thenReturn(
				testDoubleArray);
		additionMethod = new DoubleAdditionMethod(3, mockedStackCreator);

		additionMethod.calculate(4);
		assertEquals(
				"Incremental calculation of Base 5 With Exponent 3 must 125",
				125d, additionMethod.calculate(5), 0);
	}

	// Exceptions
	@Test(expected = IllegalArgumentException.class)
	public void testConstructWithNegativeExponentThrowsIllegalArgument() {
		additionMethod = new DoubleAdditionMethod(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructWithOneAsExponentThrowsIllegalArgument() {
		additionMethod = new DoubleAdditionMethod(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateWithNegativeBaseThrowsIllegalArgument()
			throws OverflowException {
		additionMethod = new DoubleAdditionMethod(4, mockedStackCreator);

		additionMethod.calculate(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCalculateWithOneAsBaseThrowsIllegalArgument()
			throws OverflowException {
		additionMethod = new DoubleAdditionMethod(4, mockedStackCreator);

		additionMethod.calculate(1);
	}

	@After
	public void tearDown() {
		additionMethod = null;
		mockedStackCreator = null;
	}
}
