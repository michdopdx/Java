package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Converter {


		public static void main(String[] args) {
				File textFile = new File(args[0]);
				File xmlFile = new File(args[1]);
				Airline airlineFromFile;

				try {
						FileReader readFromFile = new FileReader(textFile);
						TextParser parseIntoObject = new TextParser(readFromFile);
						airlineFromFile = parseIntoObject.parse();
						airlineFromFile.sortFlights();
						XmlDumper dumpParsedAirline = new XmlDumper(xmlFile);
						dumpParsedAirline.dump(airlineFromFile);


				}catch (ParserException e) {
						System.err.println(e.getMessage());
				}
				catch (InvalidParameterException e) {
						System.err.println(e.getMessage());
				}
				catch (IOException e) {
						throw new RuntimeException(e);
				}
		}
}
