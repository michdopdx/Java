package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.checkerframework.framework.qual.Covariant;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
public class ConverterTest extends InvokeMainTestCase {

		private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
				return invokeMain(Converter.class, args );
		}

		@Test
		@Disabled
		void testPerfectcase() throws IOException {
				MainMethodResult result = invokeMain("converter/test.txt","converter/copyOfvaild.xml");
				XmlParser parse = new XmlParser(new File("converter/copyOfvaild.xml"));
				int value = parse.checkXmlAirline("Jet Blue");

				assert(value == 1);
		}

}
