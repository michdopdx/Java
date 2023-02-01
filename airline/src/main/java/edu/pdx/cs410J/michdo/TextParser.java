package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;

/**
 * Class <code>TextParser</code> used to read Airline and its Flights from a file.
 * @author Michael Do
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;
  /**
   * File reader which reads characters from text files.
   */


  /**
   * This constructor will create a new instance of <code>TextParser</code>
   * @param reader File Reader
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * Will search file for given name, if the name is found or the file is empty return true.
   * @param name Name of the Airline
   * @return True if name is found or the file is empty, false otherwise.
   */
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

  /**
   * Will parse a file and create and airline and its flights
   * @return A new Airline created from parsing file.
   * @throws ParserException Will be thrown if NO Airline was found.
   */
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
