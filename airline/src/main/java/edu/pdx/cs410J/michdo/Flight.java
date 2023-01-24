package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AbstractFlight;

import java.security.InvalidParameterException;
import java.util.MissingFormatArgumentException;

public class Flight extends AbstractFlight {

  private int flightNumber;
  private String source;
  private String departure;
  private String destination;
  private String arrival;

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
    checkFormatForDataAndTime(depart);
    this.departure = depart;

    checkNullValue(dest);
    this.destination = dest;

    checkNullValue(ar);
    checkFormatForDataAndTime(ar);
    this.arrival = ar;

  }
  private static void checkNullValue(Object obj) {
    if(obj == null) {
      throw new NullPointerException("Missing Required Argument");
    }
  }
  private static void checkFormatForDataAndTime(String dataTime) {
    if(!dataTime.contains("/") || !dataTime.contains(":")) {
      throw new MissingFormatArgumentException("Missing Data or Time from departure or arrival");
    }
  }
  @Override
  public int getNumber() {
    return this.flightNumber;
  }
  @Override
  public String getSource() {
    return this.source;
  }
  @Override
  public String getDepartureString() {
    return this.departure;
  }
  @Override
  public String getDestination() {
    return this.destination;
  }

  @Override
  public String getArrivalString() {
    return this.arrival;
  }

}
