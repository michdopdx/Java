package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AbstractFlight;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.MissingFormatArgumentException;

/** Class representation of a flight.
 * @author Michael Do
 */
public class Flight extends AbstractFlight {

  private int flightNumber;
  /**
   * An integer that will denote the flight number.
   */
  private String source;
  /**
   * A String that contains three-letter code of departure airport.
   */
  private String departure;
  /**
   * A string that consist of date and time of departure.
   */
  private String destination;
  /**
   * A String that contains three-letter code of arrival airport.
   */
  private String arrival;
  /**
   * A string that consist of date and time of arrival
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
  public Flight(String flight, String src, String depart, String dest, String ar)
  {
    //checkNullValue(flight,"Flight Number");
    if (checkForFlightInt(flight)) {
      this.flightNumber = Integer.parseInt(flight);
    } else {
      throw new InvalidParameterException("Flight Number Entered Is Not A Number");
    }
		checkNullValue(src,"Source");
		if (src.length() == 3) {
				if (checkValidCode(src)) {
						this.source = src.toUpperCase();
				} else {
						throw new InvalidParameterException("The Source Code You Entered " + src + " Does not exist");
				}
		} else {
				throw new InvalidParameterException("The Source Code You Have Entered is " + src.length() + " Letters Long, Must Be Three");
		}

    if(depart != null)
    {
      if(checkFormatDateAndTime(depart)) {
        this.departure = depart;
      }
      else
        throw new InvalidParameterException(" Departure Entered Was, " + depart + " Expected, (dd/mm/yyyy hh:mm)");
    }
    else{
      throw new NullPointerException("Missing Departure");
    }

    checkNullValue(dest,"Destination");
    if (dest.length() == 3) {
      if (checkValidCode(dest)) {
        this.destination = dest.toUpperCase();
      } else {
        throw new InvalidParameterException("The Destination Code You Entered " + dest + " Does not exist");
      }
    } else {
      throw new InvalidParameterException("The Destination Code You Have Entered is " + dest.length() + " Letters Long, Must Be Three");
    }

    if(ar != null)
    {
      if(checkFormatDateAndTime(ar)) {
        this.arrival = ar;
      }
      else
        throw new InvalidParameterException(" Arrival Entered Was, " + ar + " Expected, (dd/mm/yyyy hh:mm)");
    }
    else{
      throw new NullPointerException("Missing Arrival");
    }
	}

  /**
   * Checks if a given string contains a number. If a number is present return false, else true.
   *
   * @param code Will search this string for numbers.
   * @return True if no number is fond in string else false.
   */
  public static boolean checkValidCode (String code)
  {
    char[] flightCode = code.toCharArray();
    for(char letter:flightCode) {
      if(Character.isDigit(letter))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Used to check if the format of date is in pattern MM/dd/yyyy hh:mm a.
   * @param dateTime The date of type string.
   * @return True if the argument follows the format, else false.
   */
  public static boolean checkFormatDateAndTime(String dateTime)
  {
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    format.setLenient(false);
    try{
      format.parse(dateTime);
      return true;
    }catch (ParseException e) {
      return false;
    }
  }

  /**
   * Checks if a string can be turned into a valid flight number.
   * @param check String input being checked if it is a valid number.
   * @return True if check is a valid number else false.
   */
  public static boolean checkForFlightInt (String check) {
    try {
      Integer.parseInt(check);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Checks if an object is null.
   * @param obj Object that needs to be checked.
   * @throws NullPointerException If the object is null.
   */
  public static void checkNullValue(Object obj,String type) {
    if(obj == null) {
      throw new NullPointerException("Missing " + type);
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
