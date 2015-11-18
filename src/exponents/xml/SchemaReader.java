package exponents.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class SchemaReader {

	public SchemaReader() {
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		File XsdFile = new File("Exponenten.xsd");
		File XmlFile = new File("Test.xml");

		try {
			Schema xsdSchema = factory.newSchema(XsdFile);
			Source xmlSource = new StreamSource(XmlFile);
			Validator validator = xsdSchema.newValidator();
			validator.validate(xmlSource);

			FileReader fileReader = new FileReader(XmlFile);
			BufferedReader reader = new BufferedReader(fileReader);

			String newLine = reader.readLine();

			while (newLine != null) {
				System.out.println(newLine);
				newLine = reader.readLine();
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
