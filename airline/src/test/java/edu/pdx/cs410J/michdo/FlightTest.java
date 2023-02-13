package edu.pdx.cs410J.michdo;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
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
      Flight flight = new Flight("100","abc","09/16/2000 10:30","def","9/16/2000 12:00");
      String flightString = flight.toString();
      assertThat(flightString,equalTo("Flight 100 departs ABC at 09/16/2000 10:30 arrives DEF at 9/16/2000 12:00"));
  }

  @Test
  void testNullChecker() {
    assertThrows(NullPointerException.class, () ->Flight.checkNullValue(null,"flight"));
  }
}
