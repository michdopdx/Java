package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.Collection;

/**
 * A skeletal implementation of the <code>TextDumper</code> class for Project 2.
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

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
