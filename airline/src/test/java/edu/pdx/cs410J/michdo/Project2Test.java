package edu.pdx.cs410J.michdo;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
      InputStream readme = Project3.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("********************************"));
    }
  }



  @Test
  void checkInvalidationIfStringIsInt(){
    boolean value = Project3.checkForInt("200");
    assert (value == true);
  }
  @Test
  void checkInvalidationIfStringIsNotInt(){
    boolean value = Project3.checkForInt("NotInt");
    assert (value == false);
  }

  @Test
  void checkValidDateAndTime()
  {
    boolean value = Project3.formatDateAndTime("09/16/2000 10:10 am");
    assert (value == true);
  }
  @Test
  void checkInValidDate()
  {
    boolean value = Project3.formatDateAndTime("09/xx/2000 10:10");
    assert (value == false);
  }




}
