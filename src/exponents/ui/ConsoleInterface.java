package exponents.ui;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import exponents.calculation.implementation.BigIntegerAddition;
import exponents.calculation.implementation.OverflowException;
import exponents.calculation.pattern.CalculationBigInteger;
import exponents.calculation.pattern.CalculationDouble;
import exponents.calculation.pattern.DataTypeBigInteger;
import exponents.calculation.pattern.DataTypeDouble;
import exponents.calculation.pattern.DataTypeFactory;
import exponents.calculation.pattern.DataTypeFactory.CalculationMethod;
import exponents.calculation.pattern.FactoryProducer;
import exponents.calculation.pattern.FactoryProducer.DataType;
import exponents.xml.AdditionStack;
import exponents.xml.Base;
import exponents.xml.Database;
import exponents.xml.Exponent;
import exponents.xml.StackValue;
import exponents.xml.XmlReader;

public class ConsoleInterface {

	private Scanner scanner;
	private boolean running;
	private volatile boolean calculating;
	private DataTypeFactory dataFactory;
	private CalculationBigInteger bigIntCalculation = null;
	private CalculationDouble doubleCalculation = null;
	private Database database = new Database();

	public ConsoleInterface() {
		scanner = new Scanner(System.in);
		running = true;
	}

	public void startInterface() {
		while (running) {
			readCommand();
		}
	}

	private void readCommand() {
		System.out
				.println("Commands:\n"
						+ "Calculate - starts calculation-configuration\n"
						+ "CalculateDB - starts BigInteger calculation from Database\n"
						+ "Print - Prints current Double and BigInteger calculation\n"
						+ "PrintDB - Prints current Database and all BigInteger calculations in XML Format\n"
						+ "Store - Stores the current BigInteger Addition in the Database\n"
						+ "Load - loads a BigInteger Database from an Xml File\n"
						+ "Save - Saves the current Database in an Xml File\n"
						+ "Exit - discard all unsaved entries and exit the programm.\n");
		executeCommand(scanner.nextLine());
	}

	private void executeCommand(String command) {
		if ("Exit".equals(command)) {
			running = false;
		} else if ("Load".equals(command)) {
			System.out.println("Type name of the file");
			database = XmlReader.loadXmlFile(scanner.nextLine());
		} else if ("Calculate".equals(command)) {
			String[] calcConfiguration = new String[5];
			System.out
					.println("Choose Datatype:\n" + "Double\n" + "BigInteger");
			calcConfiguration[0] = scanner.nextLine();
			System.out.println("Choose Exponent");
			calcConfiguration[1] = scanner.nextLine();
			System.out.println("Choose Base");
			calcConfiguration[2] = scanner.nextLine();
			System.out.println("Choose number of operations");
			calcConfiguration[3] = scanner.nextLine();
			System.out.println("Choose CalculationMethod:\n" + "Addition\n"
					+ "Multiplication");
			calcConfiguration[4] = scanner.nextLine();
			prepareCalculation(calcConfiguration);
		} else if ("CalculateDB".equals(command)) {
			int[] calcConfiguration = new int[3];
			System.out.println("Choose Exponent");
			calcConfiguration[0] = scanner.nextInt();
			System.out.println("Choose Base");
			calcConfiguration[1] = scanner.nextInt();
			System.out.println("Choose number of operations");
			calcConfiguration[2] = scanner.nextInt();
			scanner.nextLine();
			bigIntCalculation = loadFromDatabase(calcConfiguration[0],
					calcConfiguration[1]);
			startCalculation(bigIntCalculation, calcConfiguration[1],
					calcConfiguration[2]);
		} else if ("Print".equals(command)) {
			System.out.println("Printing Results:");
			printResults();
		} else if ("PrintDB".equals(command)) {
			System.out.println("Printing current Database:");
			XmlReader.printDatabase(database);
		} else if ("Store".equals(command)) {
			System.out.println("Storing BigIntAddition:");
			sortDatabase(database);
			storeInDatabase(bigIntCalculation);
		} else if ("Save".equals(command)) {
			System.out.println("Saving Database to File:");
			sortDatabase(database);
			System.out.println("Type the name of the File:");
			XmlReader.saveDatabase(database, scanner.nextLine());
		} else {
			System.out.println("Invalid Command, try again!");
		}
	}

	private void prepareCalculation(String[] calcConfiguration) {
		int exponent;
		int base;
		int operationsCount;

		// Choose Datatype
		if ("Double".equals(calcConfiguration[0])) {
			dataFactory = FactoryProducer
					.getCalculationDataType(DataType.DOUBLE);
		} else if ("BigInteger".equals(calcConfiguration[0])) {
			dataFactory = FactoryProducer
					.getCalculationDataType(DataType.BIGINTEGER);
		} else {
			System.err.println("Invalid input at 'Choose Datatype': "
					+ calcConfiguration[0] + " is not a valid option.");
			return;
		}

		// Check Exponent
		try {
			exponent = Integer.parseInt(calcConfiguration[1]);
		} catch (NumberFormatException e) {
			System.err.println("Invalid input at 'Choose Exponent': "
					+ calcConfiguration[1] + " is not a valid option.");
			return;
		}

		// Check Base
		try {
			base = Integer.parseInt(calcConfiguration[2]);
		} catch (NumberFormatException e) {
			System.err.println("Invalid input at 'Choose Base': "
					+ calcConfiguration[2] + " is not a valid option.");
			return;
		}

		// Check OperationsCount
		try {
			operationsCount = Integer.parseInt(calcConfiguration[3]);
		} catch (NumberFormatException e) {
			System.err
					.println("Invalid input at 'Choose number of operations': "
							+ calcConfiguration[3] + " is not a valid option.");
			return;
		}

		// Choose CalculationMethod
		if ("Addition".equals(calcConfiguration[4])) {
			if (dataFactory instanceof DataTypeDouble) {
				doubleCalculation = dataFactory.getDoubleCalculation(
						CalculationMethod.ADDITION, exponent);
			} else if (dataFactory instanceof DataTypeBigInteger) {
				bigIntCalculation = dataFactory.getBigIntegerCalculation(
						CalculationMethod.ADDITION, exponent);
			} else {
				return;
			}
		} else if ("Multiplication".equals(calcConfiguration[4])) {
			if (dataFactory instanceof DataTypeDouble) {
				doubleCalculation = dataFactory.getDoubleCalculation(
						CalculationMethod.MULTIPLICATION, exponent);
			} else if (dataFactory instanceof DataTypeBigInteger) {
				bigIntCalculation = dataFactory.getBigIntegerCalculation(
						CalculationMethod.MULTIPLICATION, exponent);
			} else {
				return;
			}
		} else {
			System.err.println("Invalid input at 'Choose CalculaionMethod': "
					+ calcConfiguration[4] + " is not a valid option.");
			return;
		}

		// Start calculation
		if (bigIntCalculation != null) {
			startCalculation(bigIntCalculation, base, operationsCount);
		} else if (doubleCalculation != null) {
			startCalculation(doubleCalculation, base, operationsCount);
		} else {
			System.err.println("Calculation failed.");
		}
	}

	private void startCalculation(CalculationBigInteger bigIntCalculation,
			int base, int operationsCount) {
		calculating = true;
		Thread thread = new Thread() {
			int baseCalc = base;
			int operationsCounter = operationsCount;

			public void run() {
				while (calculating && operationsCounter > 0) {
					bigIntCalculation.calculate(baseCalc++);
					operationsCounter--;
				}
			}
		};

		thread.start();

		System.out.println("Press enter to stop calculation");
		scanner.nextLine();
		calculating = false;

		while (thread.isAlive()) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void startCalculation(CalculationDouble doubleCalculation,
			int base, int operationsCount) {
		calculating = true;
		Thread thread = new Thread() {
			int baseCalc = base;
			int operationsCounter = operationsCount;

			public void run() {
				while (calculating && operationsCounter > 0) {
					try {
						doubleCalculation.calculate(baseCalc++);
						operationsCounter--;
					} catch (OverflowException e) {
						e.printStackTrace();
					}
				}
			}
		};

		thread.start();

		System.out.println("Press enter to stop calculation");
		scanner.nextLine();
		calculating = false;

		while (thread.isAlive()) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void printResults() {
		if (doubleCalculation != null) {
			System.out.println("Current result of Double: "
					+ doubleCalculation.getResult() + "\n" + "Base: "
					+ doubleCalculation.getBase() + ", Exponent: "
					+ doubleCalculation.getExponent());
		}
		if (bigIntCalculation != null) {
			System.out.println("Current result of BigInt: "
					+ bigIntCalculation.getResult() + "\n" + "Base: "
					+ bigIntCalculation.getBase() + ", Exponent: "
					+ bigIntCalculation.getExponent());
		}
	}

	private void storeInDatabase(CalculationBigInteger bigIntCalculation) {
		if (bigIntCalculation == null
				|| !(bigIntCalculation instanceof BigIntegerAddition)) {
			System.out.println("No valid BigIntegerAddition-Calculation found."
					+ " Only BigIntegers with AdditionStack can be stored");
			return;
		}

		BigIntegerAddition bigIntAddition = (BigIntegerAddition) bigIntCalculation;
		List<StackValue> stackValueList = new LinkedList<>();
		for (int i = 1; i < bigIntAddition.getAdditionStack().length; i++) {
			StackValue stackValue = new StackValue();
			stackValue.setDepth(i);
			stackValue.setValue(String.valueOf(bigIntAddition
					.getAdditionStack()[i]));
			stackValueList.add(stackValue);
		}

		AdditionStack additionStack = new AdditionStack();
		additionStack.setStackValue(stackValueList);

		Base base = new Base();
		base.setAdditionStack(additionStack);
		base.setBaseValue(String.valueOf(bigIntAddition.getBase()));
		base.setResult(String.valueOf(bigIntAddition.getResult()));

		Exponent exponent = new Exponent();
		boolean newExponent = true;
		for (Exponent searchExponent : database.getExponentList()) {
			if (searchExponent.getExponentValue() == bigIntAddition
					.getExponent()) {
				exponent = searchExponent;
				newExponent = false;
			}
		}
		exponent.getBaseList().add(base);
		exponent.setExponentValue(bigIntAddition.getExponent());
		exponent.setIncrement(String.valueOf(bigIntAddition
				.getExponentFactorial()));

		if (newExponent) {
			database.getExponentList().add(exponent);
		}
	}

	private BigIntegerAddition loadFromDatabase(int exponent, int base) {
		dataFactory = FactoryProducer
				.getCalculationDataType(DataType.BIGINTEGER);
		BigIntegerAddition bigIntAddition = (BigIntegerAddition) dataFactory
				.getBigIntegerCalculation(CalculationMethod.ADDITION, exponent);
		Exponent dbExponent = null;
		Base dbBase = null;
		BigInteger[] additionStack = new BigInteger[exponent - 1];

		for (Exponent searchExponent : database.getExponentList()) {
			if (searchExponent.getExponentValue() == exponent) {
				dbExponent = searchExponent;
			}
		}
		if (dbExponent == null) {
			System.err.println("No BigIntAddition with exponent: " + exponent
					+ " found in database.");
			return bigIntAddition;
		}

		for (Base searchBase : dbExponent.getBaseList()) {
			if (searchBase.getBaseValue().equals(String.valueOf(base))) {
				dbBase = searchBase;
			}
		}
		if (dbBase == null) {
			System.err.println("No BigIntAddition with base: " + base
					+ " found in database.");
			return bigIntAddition;
		}

		for (int i = 0; dbBase.getAdditionStack().getStackValue().size() < i; i++) {
			additionStack[i] = new BigInteger(dbBase.getAdditionStack()
					.getStackValue().get(i).getValue());
		}

		bigIntAddition.setBase(base);
		bigIntAddition.setAdditionStack(additionStack);
		return bigIntAddition;
	}

	private void sortDatabase(Database database) {
		// TODO
	}
}
