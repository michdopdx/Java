package edu.pdx.cs410J.michdo;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PrettyPrinter} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PrettyPrinterTest {

		@Test
		void testWriter (){
				try {
						String f = "prettyprint.txt";
						File file = new File(f);
						FileWriter wr = new FileWriter(file);
						PrettyPrinter pretty = new PrettyPrinter(wr);
				}catch (IOException e) {

				}
		}


		@Test
		void testpretty () {
				Airline airline = new Airline("Jet");
				Flight flight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:00 pm");
				airline.addFlight(flight);
				String f = "prettyprint.txt";
				String line = null;

				try {
						File file = new File(f);
						FileWriter wr = new FileWriter(file);
						PrettyPrinter pretty = new PrettyPrinter(wr);
						pretty.dump(airline);
						FileReader readPrettyFile = new FileReader(f);
						BufferedReader br = new BufferedReader(readPrettyFile);
						line = br.readLine();
						} catch (IOException e) {
				}
				assertThat(line,equalTo("Jet: Flight 100 One Way Trip from Portland, OR To Portland, OR Flight Is Scheduled To Depart From Portland, OR Airport "));
		}


		@Test
		void notSrcAirportName()
		{
				Airline airline = new Airline("Jet");
				Flight flight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:00 pm");
				airline.addFlight(flight);
				String f = "prettyprint.txt";
				String line = null;

				try {
						File file = new File(f);
						FileWriter wr = new FileWriter(file);
						PrettyPrinter pretty = new PrettyPrinter(wr);
						pretty.dump(airline);
						FileReader readPrettyFile = new FileReader(f);
						BufferedReader br = new BufferedReader(readPrettyFile);
						line = br.readLine();
				} catch (IOException e) {
				}
				assertThat(line,equalTo("Jet: Flight 100 One Way Trip from Portland, OR To Portland, OR Flight Is Scheduled To Depart From Portland, OR Airport "));
		}

		@Test
		void notDestCodeAirportName()
		{
				Airline airline = new Airline("Jet");
				Flight flight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:00 pm");
				airline.addFlight(flight);
				String f = "prettyprint.txt";
				String line = null;

				try {
						File file = new File(f);
						FileWriter wr = new FileWriter(file);
						PrettyPrinter pretty = new PrettyPrinter(wr);
						pretty.dump(airline);
						FileReader readPrettyFile = new FileReader(f);
						BufferedReader br = new BufferedReader(readPrettyFile);
						line = br.readLine();
				} catch (IOException e) {
				}
				assertThat(line,equalTo("Jet: Flight 100 One Way Trip from Portland, OR To Portland, OR Flight Is Scheduled To Depart From Portland, OR Airport "));
		}
}
