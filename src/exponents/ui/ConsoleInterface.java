package exponents.ui;

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
						+ "CalculateS - starts calculation-configuration for Sequence\n"
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
			System.out.println("Type name of the file\n");
			database = XmlReader.loadXmlFile(scanner.nextLine());
		} else if ("Calculate".equals(command)) {
			String[] calcConfiguration = new String[4];
			System.out.println("Choose Datatype:\n" + "Double\n"
					+ "BigInteger\n");
			calcConfiguration[0] = scanner.nextLine();
			System.out.println("Choose Exponent\n");
			calcConfiguration[1] = scanner.nextLine();
			System.out.println("Choose Base\n");
			calcConfiguration[2] = scanner.nextLine();
			System.out.println("Choose CalculationMethod:\n" + "Addition\n"
					+ "Multiplication\n");
			calcConfiguration[3] = scanner.nextLine();
			prepareCalculation(calcConfiguration);
		} else if ("Print".equals(command)) {
			System.out.println("Printing Results:");
			printResults();
		} else if ("Store".equals(command)) {
			System.out.println("Storing BigIntAddition:");
			storeInDatabase(bigIntCalculation);
		} else if ("PrintDB".equals(command)) {
			System.out.println("Printing current Database:");
			XmlReader.printDatabase(database);
		} else if ("Save".equals(command)) {
			System.out.println("Saving Database to File:");
			System.out.println("Type the name of the File:");
			XmlReader.saveDatabase(database, scanner.nextLine());
		} else {
			System.out.println("Invalid Command, try again!");
		}
	}

	private void prepareCalculation(String[] calcConfiguration) {
		int exponent;
		int base;

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

		// Choose CalculationMethod
		if ("Addition".equals(calcConfiguration[3])) {
			if (dataFactory instanceof DataTypeDouble) {
				doubleCalculation = dataFactory.getDoubleCalculation(
						CalculationMethod.ADDITION, exponent);
			} else if (dataFactory instanceof DataTypeBigInteger) {
				bigIntCalculation = dataFactory.getBigIntegerCalculation(
						CalculationMethod.ADDITION, exponent);
			} else {
				return;
			}
		} else if ("Multiplication".equals(calcConfiguration[3])) {
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
					+ calcConfiguration[3] + " is not a valid option.");
			return;
		}

		// Start calculation
		if (bigIntCalculation != null) {
			startCalculation(bigIntCalculation, base);
		} else if (doubleCalculation != null) {
			startCalculation(doubleCalculation, base);
		} else {
			System.err.println("Calculation failed.");
		}
	}

	private void startCalculation(CalculationBigInteger bigIntCalculation,
			final int base) {
		calculating = true;
		Thread thread = new Thread() {
			int baseCalc = base;

			public void run() {
				while (calculating) {
					bigIntCalculation.calculate(baseCalc++);
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

	private void startCalculation(CalculationDouble doubleCalculation, int base) {
		calculating = true;
		Thread thread = new Thread() {
			int baseCalc = base;

			public void run() {
				while (calculating) {
					try {
						doubleCalculation.calculate(baseCalc++);
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
		for (int i = 0; i < bigIntAddition.getAdditionStack().length - 2; i++) {
			System.err.println("Test");
			StackValue stackValue = new StackValue();
			stackValue.setDepth(i + 1);
			stackValue.setValue(String.valueOf(bigIntAddition
					.getAdditionStack()[i]));
			stackValueList.add(stackValue);
		}
		System.err.println("size of stacklist" + stackValueList.size());

		AdditionStack additionStack = new AdditionStack();
		additionStack.setStackValue(stackValueList);

		Base base = new Base();
		System.err.println(additionStack.getStackValue().size());
		base.setAdditionStack(additionStack);
		base.setBaseValue(String.valueOf(bigIntAddition.getBase()));
		base.setResult(String.valueOf(bigIntAddition.getResult()));

		Exponent exponent = new Exponent();
		exponent.getBaseList().add(base);
		exponent.setExponentValue(bigIntAddition.getExponent());
		exponent.setIncrement(String.valueOf(bigIntAddition
				.getExponentFactorial()));

		database.getExponentList().add(exponent);
	}
}
