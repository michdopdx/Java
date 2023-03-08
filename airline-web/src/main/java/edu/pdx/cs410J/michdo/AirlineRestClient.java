package edu.pdx.cs410J.michdo;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    /**
     * Sets rest client http to request http
     * @param http HTTP request
     */
    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
      this.http = http;
    }

  /**
   * Returns the airline for the given airline name.
   * @param airlineName Name of the airline which be searched for.
   * @return An airline with the given airline name.
   * @throws IOException If there is an issue with input
   * @throws ParserException Throws If there is an issue parsing XML
   */
  public Airline getAirline(String airlineName) throws IOException, ParserException {
    Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();
    XmlParser parseXml = new XmlParser(content);
    Airline airlineFromXml = parseXml.parse();

    return airlineFromXml;
  }

    /**
     * Returns the airline for the given Airline name and flights between a certain source and destination.
     * @param airlineName Name of the airline which be searched for.
     * @param src Source of search
     * @param dest Destination of search
     * @return An airline with the given airline name.
     * @throws IOException If there is an issue with input
     * @throws ParserException Throws If there is an issue parsing XML
     */
    public Airline getAirline(String airlineName, String src, String dest) throws IOException, ParserException {
        Response response = http.get(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName, AirlineServlet.FLIGHT_SRC_PARAMETER, src, AirlineServlet.FLIGHT_DEST_PARAMETER, dest));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();
        XmlParser parseXml = new XmlParser(content);
        Airline airlineFromXml = parseXml.parse();

        return airlineFromXml;
    }

    /**
     * Adds a flight to the rest client give flight and airline infomation
     * @param airlineName Name of airline
     * @param flightNumber Flight number
     * @param flightSrc Source code of flight
     * @param flightDepart Date and time of flight departure
     * @param flightDest Destination code of flight
     * @param flightArrive Date and time of flight arrival
     * @throws IOException Throws when invalid input
     */
  public void addFlight(String airlineName, String flightNumber,String flightSrc, String flightDepart, String flightDest, String flightArrive) throws IOException {
    Response response = http.post(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER, airlineName,
                                         AirlineServlet.FLIGHT_NUMBER_PARAMETER, flightNumber,
                                         AirlineServlet.FLIGHT_SRC_PARAMETER,flightSrc,
                                         AirlineServlet.FLIGHT_DEPART_PARAMETER, flightDepart,
                                         AirlineServlet.FLIGHT_DEST_PARAMETER, flightDest,
                                         AirlineServlet.FLIGHT_ARRIVE_PARAMETER, flightArrive));
    throwExceptionIfNotOkayHttpStatus(response);
  }

    /**
     * Removes all flights from client
     * @throws IOException Throws when input/output errors
     */
  public void removeAllAirline() throws IOException {
    Response response = http.delete(Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

    /**
     * Throw an exception if the Http response is not a valid status code
     * @param response The response given from http request.
     */
  private void throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getHttpStatusCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
  }

}
