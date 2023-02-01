package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.Collection;

/**
 * Class <code>TextDumper</code> used to write Airline and its Flights into file
 *
 * @author Michael Do
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;
  /**
   * File writer which writes characters to text files.
   */

  /**
   * This constructor creates an instance of <code>TextDumper</code>
   * @param writer File writer which writes characters to text files.
   */
  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * Writes contents from a given airline into a file
   *
   * @param airline Airline object which will be stored into file
   */
  @Override
  public void dump(Airline airline) {
    Collection<Flight> listOfFlights = airline.getFlights();
    PrintWriter pw = new PrintWriter(this.writer);

    try {
      pw.println(airline.getName());
      for(Flight flight:listOfFlights) {
        pw.println(flight.getNumber() + "|" +
                flight.getSource() + "|" +
                flight.getDepartureString() + "|" +
                flight.getDestination() + "|" +
                flight.getArrivalString());
      }
      pw.flush();
    } catch (Exception e) {

    }
  }
}
