package edu.pdx.cs410J.michdo;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class XmlParseTest {
		String file ="valid-airline.xml";
		File validXmlFile = new File( file);

		@Test
		void testFile() {

				XmlParser parse =  new XmlParser(validXmlFile);

		}

		@Test
		void testCheckXmlAirline() throws IOException {
				String file ="validairlinexml.xml";
				File validXmlFile = new File( file);
				XmlParser parse =  new XmlParser(validXmlFile);
				int value = parse.checkXmlAirline("Valid Airlines");
				assert (value == 1);
		}

		@Test
		void testCheckNotXmlAirline() throws IOException {
				String file ="validairlinexml.xml";
				File validXmlFile = new File( file);
				XmlParser parse =  new XmlParser(validXmlFile);
				int value = parse.checkXmlAirline("Not Valid Airlines");
				assert (value == 0);
		}

		@Test
		void testValidXml () throws ParserException {
				Airline airline;
				String file ="validairlinexml.xml";
				File validXmlFile = new File( file);
				XmlParser parse =  new XmlParser(validXmlFile);

				airline = parse.parse();
				Collection<Flight> listOfFlights = airline.getFlights();

		}

}
