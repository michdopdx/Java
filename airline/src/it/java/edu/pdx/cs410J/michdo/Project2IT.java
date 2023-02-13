package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  void filenameNoExtension ()
  {
      MainMethodResult result = invokeMain("-textFile", "Test" ,"-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
      assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs ABC at 9/16/2023 10:30 arrives DEF at 9/16/2023 12:00"));
  }

    @Test
    void filenameWithExtension ()
    {
        MainMethodResult result = invokeMain("-textFile", "Test.txt" ,"-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs ABC at 9/16/2023 10:30 arrives DEF at 9/16/2023 12:00"));
    }

    @Test
    void testWithAbsolutePathExtension ()
    {
        MainMethodResult result = invokeMain("-textFile", "../airline/Absol.txt" ,"-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs ABC at 9/16/2023 10:30 arrives DEF at 9/16/2023 12:00"));
    }
    @Test
    void testWithAbsolutePathNoExtension ()
    {
        MainMethodResult result = invokeMain("-textFile", "../airline/Absol" ,"-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs ABC at 9/16/2023 10:30 arrives DEF at 9/16/2023 12:00"));
    }


  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("It seems that you have not entered any information about the Airline or Flight Please retry again."));
  }

    @Test
    void testWithPrefectCommandLine(){
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardOut(),containsString("JetBlue: Flight 100 departs ABC at 9/16/2023 10:30 arrives DEF at 9/16/2023 12:00"));
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

    @Test
    void InvalidArrival() {
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023","10:30","def","9/16/2023/1","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Invalid Arrival"));
    }

    @Test
    void InvalidDeparture() {
        MainMethodResult result = invokeMain("-print","JetBlue","100","abc","9/16/2023/1","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Invalid Departure"));
    }

    @Test
    void invalidSrcCode() {
        MainMethodResult result = invokeMain("-print","JetBlue","100","a4c","9/16/2023","10:30","def","9/16/2023","12:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("The Source Code You Entered a4c Does not exist"));
    }

    @Test
    void testSrcCodeWithNumber()
    {
        assertThrows(InvalidParameterException.class, () -> new Flight("100","P4X","09/16/2000 10:30","PDX","9/16/2000 12:00"));
    }

    @Test
    void testWrongArrivalFormat()
    {
        assertThrows(InvalidParameterException.class, () -> new Flight("100","PDX","09/16/2000 10:30","PDX","9/16/2000/1 12:00"));
    }

    @Test
    void testMissingArrival()
    {
        assertThrows(NullPointerException.class, () -> new Flight("100","PDX","09/16/2000 10:30","PDX",null));
    }

    @Test
    void testMissingDeparture()
    {
        assertThrows(NullPointerException.class, () -> new Flight("100","PDX",null,"PDX","9/16/2000 12:00"));
    }

    @Test
    void testCodeDestWithNumber()
    {
        assertThrows(InvalidParameterException.class, () -> new Flight("100","PDX","09/16/2000 10:30","P5X","9/16/2000 12:00"));
    }

    @Test
    void testCodeDestFourLetters()
    {
        assertThrows(InvalidParameterException.class, () -> new Flight("100","PDX","09/16/2000 10:30","PXXX","9/16/2000 12:00"));
    }




}