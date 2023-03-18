package edu.pdx.cs410J.michdo;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
    /**
     * airline name
     */
    static final String AIRLINE_NAME_PARAMETER = "airline";
    /**
     * Flight Number
     */
    static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
    /**
     * Flight Source code
     */
  static final String FLIGHT_SRC_PARAMETER = "src";

    /**
     * Flight Destination code
     */
  static final String FLIGHT_DEST_PARAMETER = "dest";

    /**
     * Flight departure date and time
     */
  static final String FLIGHT_DEPART_PARAMETER = "depart";

    /**
     * Flight arrival date and time
     */
  static final String FLIGHT_ARRIVE_PARAMETER = "arrive";

    /**
     * Map if airline names to airlines
     */
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
      String src = getParameter(FLIGHT_SRC_PARAMETER,request);
      String dest = getParameter(FLIGHT_DEST_PARAMETER,request);

      if (airlineName != null && src == null && dest == null) {
          writeFlight(airlineName, response);

      } else {
          writeFlightSrcDest(airlineName,src,dest,response);
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
      if (flightNum == null) {
          missingRequiredParameter( response, FLIGHT_NUMBER_PARAMETER);
          return;
      }
      if (flightSrc == null) {
          missingRequiredParameter( response, FLIGHT_SRC_PARAMETER);
          return;
      }
      if (flightDepart == null) {
          missingRequiredParameter( response, FLIGHT_DEPART_PARAMETER);
          return;
      }
      if (flightDest == null) {
          missingRequiredParameter( response, FLIGHT_DEST_PARAMETER);
          return;
      }
      if ( flightArrive == null) {
          missingRequiredParameter( response, FLIGHT_ARRIVE_PARAMETER);
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
   * Writes the flight of the given airline name to the HTTP response.
   * @param airlineName Name of airline.
   * @param response Http servlet response.
   * @throws IOException When errors occur when dumping in xml
   *
   */
  private void writeFlight(String airlineName, HttpServletResponse response) throws IOException {
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
   * Writes airlines to the HTTP response.
   * @param response Http servlet response.
   * @throws IOException When errors occur when dumping in xml
   */
  private void writeFlightSrcDest(String airlineName,String src,String dest,HttpServletResponse response ) throws IOException
  {
      if (airlineName == null || src == null || dest == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      }

      Airline airline;
      try {
          airline = this.airlineDictionary.get(airlineName);

          PrintWriter pw = response.getWriter();
          Airline filteredAirlineWithSrcAndDest = new Airline(airlineName);
          Collection<Flight> listOfFlights = airline.getFlights();
          for (Flight flights : listOfFlights) {
              String checkSrc = flights.getSource();
              String checkDest = flights.getDestination();
              if(checkSrc.equals(src) && checkDest.equals(dest)) {
                  filteredAirlineWithSrcAndDest.addFlight(flights);
              }
          }
          XmlDumper dumpXml = new XmlDumper(pw);
          dumpXml.dump(filteredAirlineWithSrcAndDest);

      }catch (NullPointerException e) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          return;
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

    /**
     * Used for testing purposes to get the airline
     * @param airlineName name of an airline
     * @return returns the airlineDictionary
     */
  @VisibleForTesting
  Airline getAirline(String airlineName) {
      return this.airlineDictionary.get(airlineName);
  }
}
