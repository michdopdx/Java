package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * A unit test for the REST client that demonstrates using mocks and
 * dependency injection
 */
public class AirlineRestClientTest {

  @Test
  void getAllDictionaryEntriesPerformsHttpGetWithNoParameters() throws ParserException, IOException {

    String airlineName = "validAirline";
    Airline testAirline = new Airline(airlineName);
    testAirline.addFlight(new Flight("1","PDX","08/10/2022 10:00 am", "PDX","8/11/2022 12:00 pm"));
    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of(AirlineServlet.AIRLINE_NAME_PARAMETER,airlineName)))).thenReturn(airlineAsText(testAirline));
    AirlineRestClient client = new AirlineRestClient(http);
    Airline airlineFromClient = client.getAirline(airlineName);
    assertThat(airlineFromClient.getName(), equalTo(airlineName));
    assertThat(airlineFromClient.getFlights().iterator().next().getSource(),equalTo("PDX"));


  }

  private HttpRequestHelper.Response airlineAsText(Airline airline) {
    StringWriter writer = new StringWriter();

    //maybe here we xml dump??
    XmlDumper dumper = new XmlDumper(writer);
    dumper.dump(airline);

    return new HttpRequestHelper.Response(writer.toString());
  }
}
