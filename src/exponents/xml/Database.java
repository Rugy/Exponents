package exponents.xml;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Database {

	private List<Exponent> exponentList = new LinkedList<>();

	@XmlElement(name = "exponent")
	public List<Exponent> getExponentList() {
		return exponentList;
	}

	public void setExponentList(List<Exponent> exponentList) {
		this.exponentList = exponentList;
	}

}
