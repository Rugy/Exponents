package exponents.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Base {

	private String baseValue;
	private String result;
	private AdditionStack additionStack;

	@XmlAttribute
	public String getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(String baseValue) {
		this.baseValue = baseValue;
	}

	@XmlElement(name = "result")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@XmlElement(name = "additionStack")
	public AdditionStack getAdditionStack() {
		return additionStack;
	}

	public void setAdditionStack(AdditionStack additionStack) {
		this.additionStack = additionStack;
	}

}
