package exponents.xml;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class AdditionStack {

	private List<StackValue> stackValue = new LinkedList<>();

	@XmlElement(name = "stackValue")
	public List<StackValue> getStackValue() {
		return stackValue;
	}

	public void setStackValue(List<StackValue> stackValue) {
		this.stackValue = stackValue;
	}

}
