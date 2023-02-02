package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AbstractFlight;
import java.security.InvalidParameterException;
import java.util.MissingFormatArgumentException;

/** Class representation of a flight.
 * @author Michael Do
 */
public class Flight extends AbstractFlight {

  private int flightNumber;
  private String source;
  private String departure;
  private String destination;
  private String arrival;
  /**
   * Data needed to build a Flight.
   */

  /**
   * Flight constructor that creates a flight object from given arguments.
   * <p>
   * @param flight An integer that will denote the flight number.
   * @param src A String that contains three-letter code of departure airport.
   * @param depart A string that consist of date and time of departure.
   * @param dest A String that contains three-letter code of arrival airport.
   * @param ar A string that consist of date and time of arrival.
   * @throws NullPointerException Is thrown if any argument is null.
   * @throws InvalidParameterException Is thrown if the flight of the number is less than 0.
   */
  public Flight(int flight, String src, String depart, String dest, String ar)
  {
    if(flight == -1)
    {
      throw new NullPointerException("No Flight Number Was entered");
    }
    else if(flight < 0)
    {
      throw new InvalidParameterException("Flight Number Entered Is NOT Valid");
    }
    this.flightNumber = flight;

    checkNullValue(src);
    this.source = src;

    checkNullValue(depart);
    checkFormatForDateAndTime(depart);
    this.departure = depart;

    checkNullValue(dest);
    this.destination = dest;

    checkNullValue(ar);
    checkFormatForDateAndTime(ar);
    this.arrival = ar;
  }

  /**
   * Checks if an object is null.
   * <p>
   * @param obj Object that needs to be checked.
   * @throws NullPointerException If the object is null.
   *
   */
  private static void checkNullValue(Object obj) {
    if(obj == null) {
      throw new NullPointerException("Missing Required Argument");
    }
  }

  /**
   * Verifies if the date and time string has both a slash and colon.
   * <p>
   * @param dateTime A string that contains the date and the time
   * @throws MissingFormatArgumentException If the argument string does not contain slash or a colon.
   */
  private static void checkFormatForDateAndTime(String dateTime) {
    if(!dateTime.contains("/") || !dateTime.contains(":")) {
      throw new MissingFormatArgumentException("Missing Date or Time from departure or arrival");
    }
  }

  /**
   * Getter which gets the flight number of a flight.
   * @return Flight number.
   */
  @Override
  public int getNumber() {
    return this.flightNumber;
  }

  /**
   * Getter which gets the source code of a flight.
   * @return Three letter source code.
   */
  @Override
  public String getSource() {
    return this.source;
  }

  /**
   * Getter which gets the date and time of departure for a flight.
   * @return Date and time of departure.
   */
  @Override
  public String getDepartureString() {
    return this.departure;
  }

  /**
   * Getter which gets the destination code of a flight.
   * @return Three letter destination code.
   */
  @Override
  public String getDestination() {
    return this.destination;
  }

  /**
   * Getter which gets the date and time of arrival for a flight.
   * @return Date and time of arrival.
   */
  @Override
  public String getArrivalString() {
    return this.arrival;
  }

}
