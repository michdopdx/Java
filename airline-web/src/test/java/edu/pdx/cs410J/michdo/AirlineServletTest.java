package edu.pdx.cs410J.michdo;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

  @Test
  void gettingFlightForAirlineNotExist() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn("Airline");

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  @Test
  void addOneWordToDictionary() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "Airline";
    String flightNumber = "11";
    String flightSrc = "PDX";
    String flightDepart = "09/16/2000 10:00 am";
    String flightDest = "PDX";
    String flightArrive = "09/17/2000 10:00 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(flightNumber);
    when(request.getParameter(AirlineServlet.FLIGHT_SRC_PARAMETER)).thenReturn(flightSrc);
    when(request.getParameter(AirlineServlet.FLIGHT_DEPART_PARAMETER)).thenReturn(flightDepart);
    when(request.getParameter(AirlineServlet.FLIGHT_DEST_PARAMETER)).thenReturn(flightDest);
    when(request.getParameter(AirlineServlet.FLIGHT_ARRIVE_PARAMETER)).thenReturn(flightArrive);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    String xml = stringWriter.toString();
    assertThat(xml, containsString(airlineName));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());
    Airline airline = servlet.getAirline(airlineName);
    assertThat(airline.getName(), equalTo(airlineName));

    Flight flight = airline.getFlights().iterator().next();
    assertThat(flight.getNumber(),equalTo(Integer.parseInt(flightNumber)));
    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));



  }

}
