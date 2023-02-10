package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

  @Test
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    try {
      dumper.dump(airline);
    }catch (IOException e) {

    }

    String text = sw.toString();
    assertThat(text, containsString(airlineName));
  }

  @Test
  void addFlightToTextFile() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);
    Flight flight = new Flight("100","PDX", "9/9/2009 1:00 pm", "PDX", "9/9/2009 2:00 pm" );
    airline.addFlight(flight);
    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    try {
      dumper.dump(airline);
    }catch (IOException e) {

    }
    String text = sw.toString();
    assertThat(text, containsString("Test Airline\n" +
            "100|PDX|09/09/2009 01:00 PM|PDX|09/09/2009 02:00 PM"));
  }

  @Test
  void addMultiFlights() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);
    Flight flight = new Flight("100","PDX", "9/9/2009 1:00 pm", "PDX", "9/9/2009 2:00 pm" );
    airline.addFlight(flight);
    Flight flight2 = new Flight("500","PDX", "9/9/2009 1:00 pm", "PDX", "9/9/2009 2:00 pm" );
    airline.addFlight(flight2);
    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    try {
      dumper.dump(airline);
    }catch (IOException e) {

    }
    String text = sw.toString();
    assertThat(text, containsString("Test Airline\n" +
            "100|PDX|09/09/2009 01:00 PM|PDX|09/09/2009 02:00 PM\n" +
            "500|PDX|09/09/2009 01:00 PM|PDX|09/09/2009 02:00 PM"));

  }

  @Test
  void appendFlightToTextFile() {

    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    Flight flight = new Flight("100","PDX", "9/9/2009 1:00 am", "PDX", "9/9/2009 2:00 pm" );
    airline.addFlight(flight);
    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    try {
      dumper.appendFlightToFile(airline);
    }catch (IOException e) {

    }
  }


  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
  }
  @Test
  void canReadFlightAndAirlineFromFile(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
  }
}
