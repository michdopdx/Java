package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.security.InvalidParameterException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */

  @Test
    void makeACorrectFlight() {
      Flight flight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:00 am");
      String flightString = flight.toString();
      assertThat(flightString,equalTo("Flight 100 departs PDX at 9/16/00, 10:30 AM arrives PDX at 9/16/00, 12:00 AM"));
  }


  @Test
  void checkForInvalidCode() {
    boolean value;

    value = Flight.checkValidCode("A2B");
    assert (value == false);
  }
  @Test
  void checkForValidCode() {
    boolean value;
    value = Flight.checkValidCode("PDX");
    assert (value == true);
  }

  @Test
  void testCheckFormatWrongDateAndTime()
  {
    String test = "09/xx/2022 10:30 am";
    boolean value;
    value = Flight.checkFormatDateAndTime(test);
    assert(value == false);
  }

  @Test
  void testCheckFormatRightDateAndTime()
  {
    String test = "09/16/2022 10:30 am";
    boolean value;
    value = Flight.checkFormatDateAndTime(test);
    assert(value == true);
  }

  @Test
  void testCheckFormatDateAndTimeNoAmorPM()
  {
    String test = "09/16/2022 10:30";
    boolean value;
    value = Flight.checkFormatDateAndTime(test);
    assert(value == false);
  }

  @Test
  void testCheckFormatDateAndWrongTime()
  {
    String test = "09/16/2022 24:30 am";
    boolean value;
    value = Flight.checkFormatDateAndTime(test);
    assert(value == false);
  }

  @Test
  void testcodewithNumber()
  {
    assertThrows(InvalidParameterException.class, () -> new Flight("100","P4X","09/16/2000 10:30 am","PDX","9/16/2000 12:00 am"));
  }

  @Test
  void testMissingDeparture()
  {
    assertThrows(NullPointerException.class, () -> new Flight("100","PDX",null,"PDX","9/16/2000 12:00 am"));
  }
  @Test
  void testCodeDestWithNumber()
  {
    assertThrows(InvalidParameterException.class, () -> new Flight("100","PDX","09/16/2000 10:30 am","P5X","9/16/2000 12:00 am"));
  }

  @Test
  void testCodeDestTooLong()
  {
    assertThrows(InvalidParameterException.class, () -> new Flight("100","PDX","09/16/2000 10:30 am","PDDX","9/16/2000 12:00 am"));
  }

  @Test
  void testArrivalFormat()
  {
    assertThrows(InvalidParameterException.class, () -> new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/166/2000 12:00 am"));
  }

  @Test
  void testNoArrivalFormat()
  {
    assertThrows(NullPointerException.class, () -> new Flight("100","PDX","09/16/2000 10:30 am","PDX",null));
  }

  @Test
  void testCheckNull() {
    String test = null;
    assertThrows(NullPointerException.class, () -> Flight.checkNullValue(test," "));
  }

  @Test
  void testCompareFlightSameSrcMainEarly() {
    Flight mainFlight = new Flight("100","PDX","09/16/2000 10:00 am","PDX","9/16/2000 12:00 am");
    Flight compareFlight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:20 am");

    int value = mainFlight.compareTo(compareFlight);
    assert (value == -1);
  }
  @Test
  void testCompareFlightSameSrcMainLate() {
    Flight mainFlight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:00 am");
    Flight compareFlight = new Flight("100","PDX","09/16/2000 10:10 am","PDX","9/16/2000 12:20 am");

    int value = mainFlight.compareTo(compareFlight);
    assert (value == 1);
  }
  @Test
  void testComparetoSameDepart() {
    Flight mainFlight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:00 am");
    Flight compareFlight = new Flight("100","PDX","09/16/2000 10:30 am","PDX","9/16/2000 12:20 am");

    int value = mainFlight.compareTo(compareFlight);
    assertEquals(value,0);
  }
}
