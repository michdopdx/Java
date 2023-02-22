package edu.pdx.cs410J.michdo;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class XmlDumperTest {
		String file ="valid-airline.xml";
		File validXmlFile = new File( file);

		@Test
		void testFile() {
				XmlDumper dump =  new XmlDumper(validXmlFile);
		}


		@Test
		void dumpToXml() throws IOException {

				File file = new File("testDumpXml.xml");
				Airline airline = new Airline("Jet");
				Flight flight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:00 pm");
				airline.addFlight(flight);

				XmlDumper dump = new XmlDumper(file);
				dump.dump(airline);
				XmlParser parseCheck = new XmlParser(file);
				int value = parseCheck.checkXmlAirline(airline.getName());
				assert (value == 1);
		}
}
