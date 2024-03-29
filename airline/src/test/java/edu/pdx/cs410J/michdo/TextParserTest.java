package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextParserTest {

  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Test Airline"));
  }

  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  @Test
  void perfectAirlineFile() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    Collection<Flight> Flights = airline.getFlights();

    assertThat(airline.getName(), equalTo("Test Airline"));
    for(Flight flight :Flights) {
      assertThat(flight.getNumber(),equalTo(100));
      assertThat(flight.getSource(),equalTo("PDX"));
      assertThat(flight.getDepartureString(),equalTo("9/16/23, 11:30 AM"));
      assertThat(flight.getDestination(),equalTo("PDX"));
      assertThat(flight.getArrivalString(),equalTo("9/16/23, 12:30 PM"));
    }
  }

  @Test
  void invalidDate() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("invalid-airline.txt");
    assertThat(resource, notNullValue());
    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class,parser::parse);

  }

  @Test
  void CheckIfNoAirlineInFile () {
    int check;
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    TextParser parser = new TextParser(new InputStreamReader(resource));
    check = parser.checkAirline("");
    assert(check == 1);
  }

  @Test
  void CheckIfAirlineNameIsDifferent() {
    int check;
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    TextParser parser = new TextParser(new InputStreamReader(resource));
    check = parser.checkAirline("JetBlue");

    assert(check == 0);

  }


}
