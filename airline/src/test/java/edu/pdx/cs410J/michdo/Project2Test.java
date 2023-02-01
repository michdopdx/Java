package edu.pdx.cs410J.michdo;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project2Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("********************************"));
    }
  }

  @Test
  void checkInvalidationIfStringIsInt(){
    boolean value = Project2.checkForInt("200");
    assert (value == true);
  }
  @Test
  void checkInvalidationIfStringIsNotInt(){
    boolean value = Project2.checkForInt("NotInt");
    assert (value == false);
  }

  @Test
  void checkForValidDate() {
    boolean value;
    value = Project2.isValidDateAndTime("09/16/2000");
    value = Project2.isValidDateAndTime("9/6/2000");
    value = Project2.isValidDateAndTime("09/06/0001");
    assert (value == true);
  }
  @Test
  void checkForInvalidDate() {
    assertThrows(IllegalArgumentException.class, ()-> Project2.isValidDateAndTime("09/06/001"));
  }

  @Test
  void checkForValidTime(){
    boolean value;
    value = Project2.isValidDateAndTime("10:30");
    value = Project2.isValidDateAndTime("24:30");
    assert (value == true);
  }
  @Test
  void checkForInvalidTime(){
    assertThrows(InvalidParameterException.class, ()-> Project2.isValidDateAndTime("10:70"));
    assertThrows(InvalidParameterException.class, ()-> Project2.isValidDateAndTime("50:40"));
    assertThrows(InvalidParameterException.class, ()-> Project2.isValidDateAndTime("50:70"));
  }

  @Test
  void checkForInvalidCode() {
    boolean value;
    value = Project2.checkValidCode("A2B");
    assert (value == false);
  }
  @Test
  void checkForValidCode() {
    boolean value;
    value = Project2.checkValidCode("ABC");
    assert (value == true);
  }

}
