package exponents.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XmlReader {

	public static void validateSchemaOfFile(String xmlFilePath) {
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File xsdFile = new File("Exponenten.xsd");
		File xmlFile = new File(xmlFilePath);

		try {
			Schema xsdSchema = factory.newSchema(xsdFile);
			Source xmlSource = new StreamSource(xmlFile);
			Validator validator = xsdSchema.newValidator();
			validator.validate(xmlSource);
		} catch (SAXException e) {
			System.err.println(xsdFile.getPath() + " was not a valid Schema.");
			e.printStackTrace();
		} catch (IOException | IllegalArgumentException e) {
			System.err
					.println(xmlFile.getPath() + " was not a valid Xml-File.");
			e.printStackTrace();
		}
	}

	public static Database loadXmlFile(String xmlFilePath) {
		Database xmlDatabase = new Database();
		File xmlFile = new File(xmlFilePath);
		try {
			JAXBContext jaxb = JAXBContext.newInstance(Database.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			xmlDatabase = (Database) unmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			System.err.println("Database could not be loaded from "
					+ xmlFilePath + ", new Database created.");
		}
		return xmlDatabase;
	}

	public static void printDatabase(Database database) {
		try {
			JAXBContext jaxb = JAXBContext.newInstance(Database.class);
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
					"Exponenten.xsd");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(database, System.out);
		} catch (JAXBException | IllegalArgumentException e) {
			System.err.println("Database could not be printed to Console.");
			e.printStackTrace();
		}
	}

	public static void saveDatabase(Database database, String xmlFile) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				new File(xmlFile)))) {
			JAXBContext jaxb = JAXBContext.newInstance(Database.class);
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
					"Exponenten.xsd");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(database, writer);
		} catch (JAXBException | IOException | IllegalArgumentException e) {
			System.err.println("Database could not be saved in File: "
					+ xmlFile);
			e.printStackTrace();
		}
	}
}
