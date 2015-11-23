package exponents.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class AdditionStack {

	private List<StackValue> stackValue = new ArrayList<>();

	@XmlElement(name = "stackValue")
	public List<StackValue> getStackValue() {
		return stackValue;
	}

	public void setStackValue(List<StackValue> stackValue) {
		this.stackValue = stackValue;
	}

}
