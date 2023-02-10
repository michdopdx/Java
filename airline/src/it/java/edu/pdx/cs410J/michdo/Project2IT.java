package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * An integration test for the {@link Project3} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */


  @Test
  void filenameNoExtension ()
  {
      MainMethodResult result = invokeMain("-textFile", "Test" ,"-print","JetBlue","100","PDX","9/16/2023","10:30","am","PDX","9/16/2023","12:00","pm");
      assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs PDX at 9/16/23, 10:30 AM arrives PDX at 9/16/23, 12:00 PM"));
  }

    @Test
    void filenameWithExtension ()
    {
        MainMethodResult result = invokeMain("-textFile", "Test.txt" ,"-print","JetBlue","100","PDX","9/16/2023","10:30","am","PDX","9/16/2023","12:00","pm");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs PDX at 9/16/23, 10:30 AM arrives PDX at 9/16/23, 12:00 PM"));
    }
    @Test
    void testWithAbsolutePathExtension ()
    {
        MainMethodResult result = invokeMain("-textFile", "../airline/Absol.txt" ,"-print","JetBlue","100","PDX","9/16/2023","10:30","am","PDX","9/16/2023","12:00","pm");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs PDX at 9/16/23, 10:30 AM arrives PDX at 9/16/23, 12:00 PM"));
    }
    @Test
    void testWithAbsolutePathNoExtension ()
    {
        MainMethodResult result = invokeMain("-textFile", "../airline/Absol" ,"-print","JetBlue","100","PDX","9/16/2023","10:30","am","PDX","9/16/2023","12:00","pm");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs PDX at 9/16/23, 10:30 AM arrives PDX at 9/16/23, 12:00 PM"));
    }

  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("It seems that you have not entered any information"));
  }

  @Test
  void testIncorrectOptionForREADMEAndPrint(){
      MainMethodResult result = invokeMain("-readme","JetBlue","100","abc","9/16/2023","10:30","am","def","9/16/2023","12:00","am");
      assertThat(result.getTextWrittenToStandardError(), containsString("-readme option does not exist"));
  }

    @Test
    void testWithPrefectCommandLine(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","PDX","9/16/2023","10:30","am","PDX","9/16/2023","12:00","pm");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs PDX at 9/16/23, 10:30 AM arrives PDX at 9/16/23, 12:00 PM"));
    }
    @Test
    void testExceedingArguments(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","am","def","9/16/2023","12:00","am", "200","100");
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
        MainMethodResult result = invokeMain("-print","JetBlue","100a","abc","9/16/2023","10:30","am","def","9/16/2023","12:00","am");
        assertThat(result.getTextWrittenToStandardError(),containsString("Flight Number Entered Is Not A Number"));
    }
    @Test
    void testCommandLineWithoutSrc(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }
    @Test
    void testCommandLineWithInvalidSrc(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abff","9/16/2023","10:30", "am","def","9/16/2023","12:30","am");
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
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","am","def","9/16/2023","pm");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing Command Line Argument"));
    }

    @Test
    void testNullAirlineName (){
        assertThrows(NullPointerException.class, ()-> invokeMain(Project3.class,"-print",null,"100","abc","9/16/2023","10:30","def","9/16/2023"));
    }

    @Test
    void testTextfileNo() {
        MainMethodResult result = invokeMain("-textFile","-print","JetBlue","100","abc","9/16/2023","10:30","am","def","9/16/2023","pm");
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing TextFile for -textFile"));
    }

    @Test
    void InvalidDeparture() {
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023/1","10:30","am","def","9/16/2023","12:00","am");
        assertThat(result.getTextWrittenToStandardError(),containsString("Invalid Departure"));
    }

    @Test
    void InvalidArrival() {
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","am","def","9/16/2023/1","12:00","am");
        assertThat(result.getTextWrittenToStandardError(),containsString("Invalid Arrival"));
    }

    @Test
    void prettyprintonly ()
    {
        MainMethodResult result = invokeMain("-pretty","-","JetBlue","100","PDX","9/16/2023","10:30","am","PDX","9/16/2023","12:00","pm");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 One Way Trip from Portland, OR To Portland, OR Flight Is Scheduled To Depart From Portland, OR Airport \n" +
                "On Saturday, September 16, 2023, 10:30 AM, And Arriving At Portland, OR Airport at Saturday, September 16, 2023, 12:00 PM. The Duration Of The Flight is 90 Minutes"));
    }

    @Test
    void testprettyprintdashwithairlinetestfile() {
        MainMethodResult result = invokeMain("-pretty","-", "-textFile","Test","JetBlue","100","PDX","9/16/2023","10:30","am","PDX","9/16/2023","12:00","pm");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 One Way Trip from Portland, OR To Portland, OR Flight Is Scheduled To Depart From Portland, OR Airport \n" +
                "On Saturday, September 16, 2023, 10:30 AM, And Arriving At Portland, OR Airport at Saturday, September 16, 2023, 12:00 PM. The Duration Of The Flight is 90 Minutes"));
    }
}