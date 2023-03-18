package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getTextWrittenToStandardError(), containsString("No command line arguments"));
    }

    @Test
    void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project5.class, HOSTNAME, PORT );
        System.out.println(result.getTextWrittenToStandardError());
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing Host"));
    }


    @Test
    void testMissingPortNotHostName() {
        MainMethodResult result  = invokeMain(Project5.class, "-host" , HOSTNAME, "-search", "Airline");
        assertThat(result.getTextWrittenToStandardError(),equalTo("Missing Port"));

    }

    @Test
    void testMissingHostNotPort() {
        MainMethodResult result  = invokeMain(Project5.class, "-port" , PORT, "-search");
        assertThat(result.getTextWrittenToStandardError(),equalTo("Missing Host"));

    }

    @Test
    void testReadMe() {
        MainMethodResult result  = invokeMain(Project5.class, "-README");
        assertThat(result.getTextWrittenToStandardError(),containsString("*************************"));
    }

    @Test
    void searchOptionWithInvalidName() {
        String airlineName = "Airline";
        String flightNumber = "11";
        String flightSrc = "PDX";
        String flightDest = "PDX";
        MainMethodResult result  = invokeMain(Project5.class, "-port", PORT, "-print","-host",HOSTNAME ,"Airline",flightNumber,flightSrc,"09/16/2000", "10:00", "am", flightDest, "09/17/2000", "10:00", "am");
        MainMethodResult result2  = invokeMain(Project5.class, "-port", PORT, "-host",HOSTNAME ,"-search", "sdasdasdasd");
        assertThat(result2.getTextWrittenToStandardError(),containsString("Unable to search for flight with name: sdasdasdasd Src: null Dest: null"));
    }
    @Test
    void searchOptionWithNameSrcDest() {
        String airlineName = "Airline";
        String flightNumber = "11";
        String flightSrc = "ABE";
        String flightDest = "PDX";
        MainMethodResult result  = invokeMain(Project5.class, "-port", PORT, "-print","-host",HOSTNAME ,"Jet",flightNumber,flightSrc,"09/16/2000", "10:00", "am", flightDest, "09/17/2000", "10:00", "am");
        MainMethodResult result2  = invokeMain(Project5.class, "-port", PORT, "-host",HOSTNAME ,"-search", "Jet",flightSrc,flightDest);
        assertThat(result2.getTextWrittenToStandardOut(),containsString("Flights for AirLine: Jet"));
    }


    @Test
    void addFlight() {
        String airlineName = "Airline";
        String flightNumber = "11";
        String flightSrc = "PDX";
        String flightDest = "PDX";
        MainMethodResult result  = invokeMain(Project5.class, "-port", PORT, "-print","-host",HOSTNAME ,"Airline",flightNumber,flightSrc,"09/16/2000", "10:00", "am", flightDest, "09/17/2000", "10:00", "am");
        assertThat(result.getTextWrittenToStandardOut(),containsString("Airline: Flight 11 departs PDX at 9/16/00, 10:00 AM arrives PDX at 9/17/00, 10:00 AM"));
    }

    @Test
    void testWithPrefectCommandLine(){
        String airlineName = "Airline";
        String flightNumber = "11";
        String flightSrc = "PDX";
        String flightDest = "PDX";
        MainMethodResult result  = invokeMain(Project5.class, "-port", PORT, "-print","-host",HOSTNAME ,"Airline",flightNumber,flightSrc,"09/16/2000", "10:00", "am", flightDest, "09/16/2000", "11:00", "am");
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Airline: Flight 11 departs PDX at 9/16/00, 10:00 AM arrives PDX at 9/16/00, 11:00 AM"));
    }

    @Test
    void testCommandLineWithInvalidNumber(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT,"-host",HOSTNAME,"-print","JetBlue","100a","PDX","9/16/2023","10:30","am","PDX","9/16/2023","12:00","am");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Flight Number Entered Is Not A Number"));
    }

    @Test
    void testCommandLineWithInvalidSrcCode(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-print","JetBlue","100","abff","9/16/2023","10:30", "am","def","9/16/2023","12:30","am");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Invalid Source code from command line: abff"));
    }

    @Test
    void testCommandLineWithInvalidDestCode(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-print","JetBlue","100","PDX","9/16/2023","10:30", "am", "DEFF" ,"9/16/2023","12:00","am");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Invalid Destination code from command line: DEFF"));
    }

    @Test
    void testNoArgumentsButOptions(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME);
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("No Flight Arguments"));
    }
    @Test
    void testPortNotInt(){
        MainMethodResult result = invokeMain(Project5.class,"-port", "PORT", "-host",HOSTNAME,"JetBlue","100","PDX","9/16/2023","10:30", "am", "DEFF" ,"9/16/2023","12:00","am");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("** Port \"PORT\" must be an integer"));
    }

    @Test
    void testCommandLineWithInvalidDepartNotSearch(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-print","JetBlue","100","PDX","9/16/2023/1","10:30", "am","PDX","9/16/2023","12:30","am");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Invalid Departure. Departure entered was, 9/16/2023/1 10:30 am Expected Format mm/dd/yy hh:mm am/pm"));
    }

    @Test
    void testCommandLineWithInvalidDArrivalNotSearch(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-print","JetBlue","100","PDX","9/16/2023","10:30", "am","PDX","9/16/2023/1","12:30","am");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Invalid Arrival. Arrival entered was, 9/16/2023/1 12:30 am Expected Format mm/dd/yy hh:mm am/pm"));
    }

    @Test
    void testCommandLineWithToManyFlightArgs(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-print","JetBlue","100","PDX","9/16/2023","10:30", "am","PDX","9/16/2023","11:30","am","hello world");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Extra Arguments: \n" +
                " Arguments you entered: [JetBlue, 100, PDX, 9/16/2023, 10:30, am, PDX, 9/16/2023, 11:30, am, hello world]"));
    }

    @Test
    void testCommandLineWithToLittleFlightArgs(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-print","JetBlue","100","PDX","9/16/2023","10:30", "am","PDX","9/16/2023","11:30");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Missing Arguments: \n" +
                " Arguments you entered: [JetBlue, 100, PDX, 9/16/2023, 10:30, am, PDX, 9/16/2023, 11:30]"));
    }


    @Test
    void testSearchInvalidSrc(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-search","JetBlue", "asd","PDX");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Invalid Source code from command line: asd"));
    }

    @Test
    void testSearchInvalidDest(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-search","JetBlue", "PDX","asd");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Invalid Destination code from command line: asd"));
    }

    @Test
    void testSearchWithTooManyArgs(){
        MainMethodResult result = invokeMain(Project5.class,"-port", PORT, "-host",HOSTNAME,"-search","JetBlue", "PDX","PDX","helloworld");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Search Command does not support [JetBlue, PDX, PDX, helloworld]"));
    }


    @Test
    void testCase1() {
        MainMethodResult result = invokeMain(Project5.class, "-README");
        assertThat(result.getTextWrittenToStandardError(),containsString("* -README") );
    }
    @Test
    void testCase2() {
        MainMethodResult result = invokeMain(Project5.class);
        assertThat(result.getTextWrittenToStandardError(),containsString("usage: java Project5 host port [airlineName] [flightNumber] [flightSrc] [flightDepart] [flightDest] [flightArrive]") );
    }


}