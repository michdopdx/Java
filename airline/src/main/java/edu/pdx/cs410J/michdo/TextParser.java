package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;

/**
 * A skeletal implementation of the <code>TextParser</code> class for Project 2.
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  public boolean checkAirline(String name) {
    try {
      BufferedReader br = new BufferedReader(this.reader);
      String line = br.readLine();
      if(br.readLine() == null || line.equals(name)) {
        return true;
      }
    }catch (Exception e) {

    }
    return false;
  }
  @Override
  public Airline parse() throws ParserException {
    String line;
    String airlineName = null;

    String flightNumber = null;
    String flightSrc = null;
    String flightDepart = null;
    String flightDest = null;
    String flightArrival = null;
    Airline airline = null;
    Flight flight = null;

    BufferedReader br = new BufferedReader(this.reader);


    try {
      while((line = br.readLine()) != null) {

        if (!line.contains("|")) {
          airlineName = line;
          airline = new Airline(airlineName);
        }
        if (line.contains("|")) {
          String[] flightData = line.split("\\|");
          flightNumber = flightData[0];
          flightSrc = flightData[1];
          flightDepart = flightData[2];
          flightDest = flightData[3];
          flightArrival = flightData[4];
        }
      }
      if(airlineName != null) {
        try {
          flight = new Flight(Integer.parseInt(flightNumber), flightSrc, flightDepart, flightDest, flightArrival);
        } catch (Exception e) {

        }
      }
      else {
        throw new ParserException("");
      }

      airline.addFlight(flight);
      return airline;


    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
