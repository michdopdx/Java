package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.Comparable;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.MissingFormatArgumentException;

/** Class representation of a flight.
 * @author Michael Do
 */
public class Flight extends AbstractFlight implements Comparable<Flight>{

  private int flightNumber;
  /**
   * An integer that will denote the flight number.
   */
  private String source;
  /**
   * A String that contains three-letter code of departure airport.
   */
  private Date departure;
  /**
   * A string that consist of date and time of departure.
   */
  private String destination;
  /**
   * A String that contains three-letter code of arrival airport.
   */
  private Date arrival;
  /**
   * A string that consist of date and time of arrival
   */

  /**
   * Flight constructor that creates a flight object from given arguments.
   *
   * @param flight An integer that will denote the flight number.
   * @param src A String that contains three-letter code of departure airport.
   * @param depart A string that consist of date and time of departure.
   * @param dest A String that contains three-letter code of arrival airport.
   * @param ar A string that consist of date and time of arrival.
   * @throws NullPointerException Is thrown if any argument is null.
   * @throws InvalidParameterException Is thrown if the flight of the number is less than 0.
   */
  public Flight(String flight, String src, String depart, String dest, String ar) throws InvalidParameterException,NullPointerException {

   checkNullValue(flight,"Flight Number");
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
        this.departure = formatDateAndTime(depart);
      }
      else
        throw new InvalidParameterException(" Departure Entered Was, " + depart + " Expected, (dd/mm/yyyy hh:mm am/pm)");
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
        this.arrival = formatDateAndTime(ar);
      }
      else
        throw new InvalidParameterException(" Arrival Entered Was, " + ar + " Expected, (dd/mm/yyyy hh:mm)");
    }
    else{
      throw new NullPointerException("Missing Arrival");
    }

    if(timeDifference(this.departure,this.arrival))
    {
      throw new InvalidParameterException("Arrival time Comes before Departure. Departure time MUST come before Arrival");
    }
  }

  /**
   * Use to check whether Departure date comes before Arrival date.
   * @param departure The date which the flight leaves.
   * @param arrival The date which the flight lands
   * @return True if the Departure time is less than Arrival time, else false.
   */
  public boolean timeDifference (Date departure, Date arrival) {
    if(departure.getTime() > arrival.getTime())
    {
      return true;
    }
    return false;
  }

  /**
   * Format date string with MM/dd/yyyy hh:mm a
   * @param date Given date in string form.
   * @return returns formatted string with MM/dd/yyyy hh:mm a.
   */
  public Date formatDateAndTime(String date) {
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    try{
      Date temp = format.parse(date);
      return temp;
    }catch (ParseException e) {
    }
    return null;
  }

  /**
   * Checks if an object is null.
   * <p>
   * @param obj Object that needs to be checked.
   * @throws NullPointerException If the object is null.
   */
  public static void checkNullValue(Object obj,String type) {
    if(obj == null) {
      throw new NullPointerException("Missing " + type);
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
   * Checks if a given string contains a number. If a number is present return false, else true.
   *
   * @param code Will search this string for numbers.
   * @return True if no number is fond in string else false.
   */
  public static boolean checkValidCode (String code)
  {
    String check = AirportNames.getName(code);
    if(check != null) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Used to check if the format of date is in pattern MM/dd/yyyy hh:mm a.
   * @param dateTime The date of type string.
   * @return True if the argument follows the format, else false.
   */
  public static boolean checkFormatDateAndTime(String dateTime)
  {
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    format.setLenient(false);
    try{
      format.parse(dateTime);
      return true;
    }catch (ParseException e) {
      return false;
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
   *  Gets the departure date/time for flight
   * @return The Departure date/time of a given flight
   */
  public Date  getDeparture()
  {
   return this.departure;
  }

  /**
   * Gets the arrival date/time for flight
   * @return The Arrival date/time for a given flight
   */
  public Date getArrival()
  {
    return this.arrival;
  }
  /**
   * Getter which gets the date and time of departure for a flight.
   * @return Date and time of departure.
   */
  @Override
  public String getDepartureString() {
    DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT , Locale.US);
    return format.format(this.departure);
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
    DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, Locale.US);
    return format.format(this.arrival);  }

  /**
   * Gets the duration of the flights in minutes.
   * @return Duration in minutes as a string.
   */
  public String getFlightDuration()
  {
    long duration = this.arrival.getTime() -  this.departure.getTime();
    long second = duration / 1000;
    long min = second/60;

    return Long.toString(min);
  }

  /**
   * Used to compare two Flights.
   * @param o the object to be compared.
   * @return -1 if current flight is larger alphabetically, 1 if o is greater alphabetically, and 0 if the flights are equal
   */
  @Override
  public int compareTo(Flight o) {
    int result = (this.source.compareTo(o.getSource()));
    if(result < 0){
      return -1;
    }
    if(result > 0) {
      return 1;
    }
    if(result == 0) {
      return this.departure.compareTo(o.departure);
    }
    return result;
  }
}
