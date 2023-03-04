package edu.pdx.cs410J.michdo;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
  static final String AIRLINE_NAME_PARAMETER = "airline";
  static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
  static final String FLIGHT_SRC_PARAMETER = "src";
  static final String FLIGHT_DEST_PARAMETER = "dest";
  static final String FLIGHT_DEPART_PARAMETER = "depart";
  static final String FLIGHT_ARRIVE_PARAMETER = "arrive";

  private final Map<String, Airline> airlineDictionary = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all of the entries in the dictionary
   * are written to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );
      if (airlineName != null) {
          writeDefinition(airlineName, response);

      } else {
          writeAllDictionaryEntries(response);
      }
  }

  /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );
      String flightNum = getParameter(FLIGHT_NUMBER_PARAMETER, request );
      String flightSrc = getParameter(FLIGHT_SRC_PARAMETER, request );
      String flightDepart = getParameter(FLIGHT_DEPART_PARAMETER, request );
      String flightDest = getParameter(FLIGHT_DEST_PARAMETER, request );
      String flightArrive = getParameter(FLIGHT_ARRIVE_PARAMETER, request );

      if (airlineName == null) {
          missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
          return;
      }
      if ( flightNum == null) {
          missingRequiredParameter( response, FLIGHT_NUMBER_PARAMETER);
          return;
      }
      
      Airline postAirline = this.airlineDictionary.get(airlineName);
      if(postAirline == null) {
          postAirline = new Airline(airlineName);
          this.airlineDictionary.put(airlineName,postAirline);
      }
      Flight postFlight = new Flight(flightNum,flightSrc,flightDepart,flightDest,flightArrive);
      postAirline.addFlight(postFlight);

      PrintWriter pw = response.getWriter();
      XmlDumper dumpXml = new XmlDumper(pw);
      dumpXml.dump(postAirline);

      response.setStatus( HttpServletResponse.SC_OK);
  }

  /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/plain");

      this.airlineDictionary.clear();

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allDictionaryEntriesDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes the definition of the given word to the HTTP response.
   *
   * The text of the message is formatted with {@link TextDumper}
   */
  private void writeDefinition(String airlineName, HttpServletResponse response) throws IOException {
    Airline airline = this.airlineDictionary.get(airlineName);

    if (airline == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);

    } else {
      PrintWriter pw = response.getWriter();
      XmlDumper dumpXml = new XmlDumper(pw);
      dumpXml.dump(airline);

      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  /**
   * Writes all of the dictionary entries to the HTTP response.
   *
   * The text of the message is formatted with {@link TextDumper}
   */
  private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
  {
      //when we want to add all to xml we will use the airlinedictionary and loop through that hash map
      //and each foreach of the airline in the airlinedic will xmldump

      PrintWriter pw = response.getWriter();
     for(Map.Entry<String,Airline> airlineEntry:this.airlineDictionary.entrySet()){
         XmlDumper dumpXml = new XmlDumper(pw);
         dumpXml.dump(airlineEntry.getValue());
     }
      response.setStatus( HttpServletResponse.SC_OK );
  }

  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

  @VisibleForTesting
  Airline getAirline(String airlineName) {
      return this.airlineDictionary.get(airlineName);
  }
}
