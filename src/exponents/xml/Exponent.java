package exponents.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "increment", "baseList" })
public class Exponent {

	private int exponentValue;
	private String increment;
	private List<Base> baseList = new ArrayList<>();

	@XmlAttribute
	public int getExponentValue() {
		return exponentValue;
	}

	public void setExponentValue(int exponentValue) {
		this.exponentValue = exponentValue;
	}

	@XmlElement(name = "increment")
	public String getIncrement() {
		return increment;
	}

	public void setIncrement(String increment) {
		this.increment = increment;
	}

	@XmlElement(name = "base")
	public List<Base> getBaseList() {
		return baseList;
	}

	public void setBaseList(List<Base> baseList) {
		this.baseList = baseList;
	}

}
