package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */


  @Test
  @Disabled
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("It seems that you have not entered any information about the Airline or Flight Please retry again.\n" +
            "Format: java -jar target/airline-2023.0.0.jar [options] <args> \n " +
            "Options(Are optional)\n " +
            "*: -README -> Prints out the README \n " +
            "*: -print  -> Prints the description of the flight \n" +
            "Args: (IN THIS ORDER) \n" +
            "*: Airline Name -> Name of the airline being added \n" +
            "*: Flight Number-> The number of the Flight being added \n" +
            "*: Source       -> 3-letter code of departure airport \n" +
            "*: Departure data -> The date of departure(Formatted as mm/dd/yyyy)\n" +
            "*: Departure time -> The time of departure(Formatted as hh:mm \n" +
            "*: Destination ->  3-letter code of destination airport \n" +
            "*: Arrival data -> The date of arrival(Formatted as mm/dd/yyyy) \n" +
            "*: Departure time -> The time of departure(Formatted as hh:mm \n " +
            "An example: java -jar target/airline-2023.0.0.jar -print JetBlue 100 abc 9/16/2023 10:30 def 9/16/2023 12:30\n"));
  }

  @Test
    void testIncorrectOptionForREADMEAndPrint(){
      MainMethodResult result = invokeMain("-readme","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
      assertThat(result.getTextWrittenToStandardError(), containsString("-readme option does not exist"));
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
    void testCommandLineWithOutName(){
        MainMethodResult result = invokeMain("-print","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }
    @Test
    void testCommandLineWithOutNumber(){
        MainMethodResult result = invokeMain("-print","JetBlue","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }
    @Test
    void testCommandLineWithInvalidNumber(){
        MainMethodResult result = invokeMain("-print","JetBlue","100a","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Flight Number Entered Is Not A Number"));
    }

    @Test
    void testCommandLineNoFlightNumber(){
        MainMethodResult result = invokeMain("-print","JetBlue","100a","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Flight Number Entered Is Not A Number"));
    }

    @Test
    void testCommandLineWithoutSrc(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }
    @Test
    void testCommandLineWithInvalidSrc(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abff","9/16/2023","10:30","def","9/16/2023","12:30");
        assertThat(result.getTextWrittenToStandardError(),containsString("The Source Code You Have Entered is 4 Letters Long, Must Be Three"));
    }
    @Test
    void testCommandLineWithoutArrival(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }
    @Test
    void testCommandLineWithInvalidArrival(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }

    @Test
    void testCommandLineWithoutDepartDate(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }

    @Test
    void testCommandLineWithoutDepartTime(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }

    @Test
    void testCommandLineArrivalWithoutDate(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","def","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }

    @Test
    void testCommandLineWithoutArrivalTime(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }




}