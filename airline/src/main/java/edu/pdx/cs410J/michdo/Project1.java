package edu.pdx.cs410J.michdo;

import com.google.common.annotations.VisibleForTesting;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.checkerframework.checker.units.qual.A;

import javax.management.InstanceNotFoundException;
import javax.sound.midi.Soundbank;
import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.MissingFormatArgumentException;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {


  static boolean isValidDateAndTime(String dateAndTime) {
    if (dateAndTime.contains("/")) { //Checking for the data
      int firstSlash = dateAndTime.indexOf("/");
      int secondSlash = dateAndTime.indexOf("/", firstSlash + 1);

      if (firstSlash > 0 && secondSlash > 0) {
        String month = dateAndTime.substring(0, firstSlash);
        String day = dateAndTime.substring(firstSlash + 1, secondSlash);
        String year = dateAndTime.substring(secondSlash + 1, dateAndTime.length());
        int months;
        int days;
        int years;

        if (checkForInt(month) && checkForInt(day) && checkForInt(year)) {
          months = Integer.parseInt(month);
          days = Integer.parseInt(day);
          years = Integer.parseInt(year);
          if (months <= 12 && days <= 31 && years == 2023) {
            return true;
          } else {
            throw new InvalidParameterException();
          }
        } else {
          throw new IllegalArgumentException();
        }
      }
    }

    if (dateAndTime.contains(":")) {//Checking for time
      int colonLocation = dateAndTime.indexOf(":");
      String hour = dateAndTime.substring(0, colonLocation);
      String min = dateAndTime.substring(colonLocation + 1, dateAndTime.length());

      if (checkForInt(hour) && checkForInt(min)) {
        int hours = Integer.parseInt(hour);
        int minuets = Integer.parseInt(min);
        if (hours <= 24 && minuets <= 59) {
          return true;
        } else {
          throw new InvalidParameterException();
        }
      } else {
        throw new IllegalArgumentException();
      }
    }
    return false;
  }

  public static boolean checkForInt(String check) {
    try {
      Integer.parseInt(check);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static void main(String[] args) {
    boolean hasREADME = false;
    boolean hasPrint = false;
    int options = 0;
    String name = null;
    int flightNum = -1;
    String src = null;
    String dest = null;
    String depart = "";
    String arrival = "";

    if (args.length == 0) {
      System.err.println("Missing command line arguments");
    } else {
      for(int i = 0; i < args.length; i++)
      {
        if(args[i].contains("-README") || args[i].contains("-print")){
          options ++;
        }
      }
      if(args.length - options > 8) {
        System.err.println("The Number Of Arguments Has Exceeded The Limits");
      }

      else {
        for (String arg : args) {
          if (arg.contains("README") || arg.contains("print")) {
            if (arg.equals("-README")) {
              hasREADME = true;
            } else if (arg.equals("-print")) {
              hasPrint = true;
            } else
              System.err.println("Possible options are \"-README\" or \"-print\"");
          } else if (arg.contains(" ") || name == null && arg.length() > 3 && (!isValidDateAndTime(arg) && !checkForInt(arg)) ) {
            name = arg;
          } else if (checkForInt(arg) && flightNum == -1) {
            flightNum = Integer.parseInt(arg);
          } else if (arg.length() == 3 && (src == null || dest == null)) {
            if (src == null) {
              src = arg;
            } else
              dest = arg;
          } else if (arg.contains(":") || arg.contains("/")) {
            try {
              if (isValidDateAndTime(arg)) {
                if (depart.isEmpty()) {
                  depart = arg;
                } else if (arg.contains(":") && arrival.isEmpty()) {
                  depart = depart + " " + arg;
                } else if (arrival.isEmpty()) {
                  arrival = arg;
                } else if (!depart.isEmpty() && !arrival.isEmpty()) {
                  arrival = arrival + " " + arg;
                }
              }
            } catch (InvalidParameterException e) {
              System.err.println("Invalid Time or Date Has been entered");
              return;
            } catch (IllegalArgumentException e) {
              System.err.println("Time or data did not contain a number. Please enter numbers for time and date");
              return;
            }

          }
        }

        try {
          Airline check = new Airline(name);
        } catch (NullPointerException e) {
          System.err.println(e.getMessage());
          return;
        }

        Airline airline = new Airline(name);
        try {
          Flight flight = new Flight(flightNum, src, depart, dest, arrival);
          airline.addFlight(flight);
        } catch (NullPointerException e) {
          System.err.println(e.getMessage());
          return;
        } catch (InvalidParameterException e) {
          System.err.println(e.getMessage());
          return;
        } catch (MissingFormatArgumentException e) {
          System.err.println(e.getMessage());
        }
        if (hasREADME) {
          System.out.println("This is going to be the read me");
        }

        if (hasPrint) {

          Collection<Flight> listOfFlights = airline.getFlights();
          for (Flight flights : listOfFlights) {
            System.out.println(airline.getName() + ": " + flights.toString());
          }
        }
      }
    }
  }
}
