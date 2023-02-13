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
  void checkForValidDateTime() {
    boolean value;
    value = Project2.formatDateAndTime("09/16/2000 10:00");
    assert (value == true);
  }

  @Test
  void checkInvalidDate() {
    boolean value;
    value = Project2.formatDateAndTime("09/xx/2000 10:00");
    assert (value == false);
  }

  @Test
  void checkInvalidTime() {
    boolean value;
    value = Project2.formatDateAndTime("09/16/2000 xx:00");
    assert (value == false);
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
