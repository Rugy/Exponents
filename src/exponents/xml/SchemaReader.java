package exponents.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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

			JAXBContext jaxb = JAXBContext.newInstance(Database.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Database xmlDatabase = (Database) unmarshaller.unmarshal(XmlFile);

			System.err.println(xmlDatabase.getExponentList().get(0)
					.getExponentValue());

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
