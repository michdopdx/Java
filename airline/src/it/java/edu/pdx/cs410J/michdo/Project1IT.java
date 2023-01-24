package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */


  @Test
  @Disabled
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
  @Disabled
    void testIncorrectOptionForREADMEAndPrint(){
      MainMethodResult result = invokeMain("-readme");
      assertThat(result.getTextWrittenToStandardError(), containsString("Possible options are \"-README\" or \"-print\""));
  }
    @Test
    @Disabled
    void testCorrectOptionForREADMEAndPrint(){
        MainMethodResult result = invokeMain("-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("this is the read me"));
    }

    @Test
    void testWithPrefectCommandLine(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs abc at 9/16/2023 10:30 arrives def at 9/16/2023 12:00"));
    }
    @Test
    void testExceedingArguments(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023","12:00","xyz","300");
        assertThat(result.getTextWrittenToStandardError(),containsString("The Number Of Arguments Has Exceeded The Limits"));
    }
    @Test
    @Disabled
    void testCommandLineWithOutName(){
        MainMethodResult result = invokeMain("-print","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Name of airline is invalid or has not been entered"));
    }

    @Test
    void testCommandLineWithOutNumber(){
        MainMethodResult result = invokeMain("-print","JetBlue","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("No Flight Number Was entered"));
    }
    @Test
    void testCommandLineWithInvalidNumber(){
        MainMethodResult result = invokeMain("-print","JetBlue","-100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Flight Number Entered Is NOT Valid"));
    }

    @Test
    void testCommandLineNoFlightNumber(){
        MainMethodResult result = invokeMain("-print","JetBlue","100a","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("No Flight Number Was entered"));
    }

    @Test
    void testCommandLineWithoutSrc(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Required Argument"));
    }

    @Test
    void testCommandLineWithoutArrival(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Required Argument"));
    }

    @Test
    void testCommandLineWithoutDepartDate(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Data or Time from departure or arrival"));
    }

    @Test
    void testCommandLineWithoutDepartTime(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Data or Time from departure or arrival"));
    }

    @Test
    void testCommandLineArrivalWithoutDate(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","def","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Data or Time from departure or arrival"));
    }

    @Test
    void testCommandLineWithoutArrivalTime(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Data or Time from departure or arrival"));
    }



}