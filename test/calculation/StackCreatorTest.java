package calculation;

import java.math.BigInteger;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class StackCreatorTest {

	StackCreator creator = new StackCreatorImpl();

	// CreateBigIntegerStack
	@Test
	public void testBigIntegerStackWithBaseOneExponentThree() {
		BigInteger[] expectedStack = new BigInteger[3];
		expectedStack[0] = BigInteger.valueOf(27);
		expectedStack[1] = BigInteger.valueOf(19);
		expectedStack[2] = BigInteger.valueOf(12);

		assertArrayEquals(
				"Returned additionStack with Base 1 and Exponent 3 must be 27, 19, 6",
				expectedStack, creator.createBigIntegerStack(1, 3));
	}

	@Test
	public void testBigIntegerStackWithBaseTwoExponentThree() {
		BigInteger[] expectedStack = new BigInteger[3];
		expectedStack[0] = BigInteger.valueOf(64);
		expectedStack[1] = BigInteger.valueOf(37);
		expectedStack[2] = BigInteger.valueOf(18);

		assertArrayEquals(
				"Returned additionStack with Base 2 and Exponent 3 must be 64, 37, 18",
				expectedStack, creator.createBigIntegerStack(2, 3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBigIntegerStackWithNegativeBaseThrowsException() {
		creator.createBigIntegerStack(-1, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBigIntegerStackWithNegativeExponentThrowsException() {
		creator.createBigIntegerStack(2, -1);
	}

	// CreateDoubleStack
	@Test
	public void testDoubleStackWithBaseOneExponentThree()
			throws OverflowException {
		double[] expectedStack = new double[3];
		expectedStack[0] = 27;
		expectedStack[1] = 19;
		expectedStack[2] = 12;

		assertArrayEquals(
				"Returned additionStack with Base 1 and Exponent 3 must be 27, 19, 6",
				expectedStack, creator.createDoubleStack(1, 3), 0);
	}

	@Test
	public void testDoubleStackWithBaseTwoExponentThree()
			throws OverflowException {
		double[] expectedStack = new double[3];
		expectedStack[0] = 64;
		expectedStack[1] = 37;
		expectedStack[2] = 18;

		assertArrayEquals(
				"Returned additionStack with Base 2 and Exponent 3 must be 64, 37, 18",
				expectedStack, creator.createDoubleStack(2, 3), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDoubleStackWithNegativeBaseThrowsException() {
		creator.createBigIntegerStack(-1, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDoubleStackWithNegativeExponentThrowsException() {
		creator.createBigIntegerStack(2, -1);
	}

	// DoubleToBigInteger
	@Test
	public void testDoubleToIntegerConversion() {
		double testDouble = 20.0;

		assertEquals("Returned object must be a BigInteger",
				BigInteger.valueOf((int) testDouble),
				creator.doubleToBigInteger(testDouble));
	}

	// FactorialRecursively
	@Test
	public void testFactorialRecuriveOfFiveEqualsOneTwenty() {
		assertEquals("Recusive factorial of 5 must be 120", 120,
				creator.factorialRecursively(5), 0);
	}

	@Test
	public void testFactorialRecuriveOfZeroEqualsOne() {
		assertEquals("Recusive factorial of 0 must be 1", 1,
				creator.factorialRecursively(0), 0);
	}

	@Test
	public void testFactorialRecursiveHigherThanTwelve() {
		assertEquals("Recursive factorial of 13 must call other method",
				6227020800.0, creator.factorialRecursively(13), 0);
	}

	@Test(expected = ArithmeticException.class)
	public void testFactorialRecursiveNegativeThrowsException() {
		creator.factorialRecursively(-1);
	}

	// FactorialIteratively
	@Test
	public void testFactorialIterativeOfFiveEqualsOneTwenty() {
		assertEquals("Iterative factorial of 5 must be 120", 120,
				creator.factorialIteratively(5), 0);
	}

	@Test
	public void testFactorialIterativeOfZeroEqualsOne() {
		assertEquals("Iterative factorial of 0 must be 1", 1,
				creator.factorialIteratively(0), 0);
	}

	@Test(expected = ArithmeticException.class)
	public void testFactorialIterativeNegativeThrowsException() {
		creator.factorialIteratively(-1);
	}

	// FactorialBigInteger
	@Test
	public void testFactorialBigIntegerOfFiveEqualsOneTwenty() {
		assertEquals("BigInteger factorial of 5 must be 120", 120,
				creator.factorialIteratively(5), 0);
	}

	@Test
	public void testFactorialBigIntegerOfZeroEqualsOne() {
		assertEquals("BigInteger factorial of 0 must be 1", 1,
				creator.factorialIteratively(0), 0);
	}

	@Test
	public void testFactorialBigIntegerOfLargeNumberComputes() {
		creator.factorialBigIngeter(500);
	}

	@Test(expected = ArithmeticException.class)
	public void testFactorialBigIntegerNegativeThrowsException() {
		creator.factorialIteratively(-1);
	}

	// MultiplyDouble
	@Test
	public void testMultiplyDoubleFourAndFiveEqualsTwenty()
			throws OverflowException {
		assertEquals("Double multiplication 4 with 5 must be 20", 20.0,
				creator.multiplyDouble(4.0, 5.0), 0);
	}

	@Test
	public void testMultiplyDoubleNegativeOneAndFiveEqualsNegativeFive()
			throws OverflowException {
		assertEquals("Double multiplication -1 with 5 must be -5", -5.0,
				creator.multiplyDouble(-1.0, 5.0), 0);
	}

	@Test
	public void testMultiplyDoubleNegativeOneAndNegativeFiveEqualsFive()
			throws OverflowException {
		assertEquals("Double multiplication -1 with -5 must be 5", 5.0,
				creator.multiplyDouble(-1.0, -5.0), 0);
	}

	@Test
	public void testMultiplyDoubleFiveAndZeroEqualsZero()
			throws OverflowException {
		assertEquals("Double multiplication 5 with 0 must be 0", 0.0,
				creator.multiplyDouble(5.0, 0.0), 0);
	}

	@Test
	public void testMultiplyDoubleZeroAndFiveEqualsZero()
			throws OverflowException {
		assertEquals("Double multiplication 0 with 5 must be 0", 0.0,
				creator.multiplyDouble(0.0, 5.0), 0);
	}

	@Test(expected = OverflowException.class)
	public void testMultiplyDoubleVeryHighAndVeryHighThrowsOverflowException()
			throws OverflowException {
		creator.multiplyDouble(100_000_000, 10E300);
	}

	// MultiplyBigInteger
	@Test
	public void testMultiplyBigIntegerFourAndFiveEqualsTwenty()
			throws OverflowException {
		assertEquals("BigInteger multiplication 4 with 5 must be 20",
				BigInteger.valueOf(20),
				creator.multiplyBigInt(4.0, BigInteger.valueOf(5)));
	}

	@Test
	public void testMultiplyBigIntegerNegativeOneAndFiveEqualsNegativeFive()
			throws OverflowException {
		assertEquals("BigInteger multiplication -1 with 5 must be -5",
				BigInteger.valueOf(-5),
				creator.multiplyBigInt(-1.0, BigInteger.valueOf(5)));
	}

	@Test
	public void testMultiplyBigIntegerNegativeOneAndNegativeFiveEqualsFive()
			throws OverflowException {
		assertEquals("BigInteger multiplication -1 with -5 must be 5",
				BigInteger.valueOf(5),
				creator.multiplyBigInt(-1.0, BigInteger.valueOf(-5)));
	}

	@Test
	public void testMultiplyBigIntegerFiveAndZeroEqualsZero()
			throws OverflowException {
		assertEquals("BigInteger multiplication 5 with 0 must be 0",
				BigInteger.valueOf(0),
				creator.multiplyBigInt(5, BigInteger.valueOf(0)));
	}

	@Test
	public void testMultiplyBigIntegerZeroAndFiveEqualsZero()
			throws OverflowException {
		assertEquals("BigInteger multiplication 0 with 5 must be 0",
				BigInteger.valueOf(0),
				creator.multiplyBigInt(0, BigInteger.valueOf(5)));
	}
}