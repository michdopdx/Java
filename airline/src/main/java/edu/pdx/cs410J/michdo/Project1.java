package edu.pdx.cs410J.michdo;

import com.google.common.annotations.VisibleForTesting;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import edu.pdx.cs410J.ParserException;
import org.checkerframework.checker.units.qual.A;

import javax.management.InstanceNotFoundException;
import javax.sound.midi.Soundbank;
import java.io.*;
import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  /**
   * Returns true or false depending on if the argument passes is a valid date or time.
   * The argument will contain a colon which indicates time, or / for dates.
   * The dateAndTime will then be parsed into their respective components.
   * The components will then be verified.
   *
   * @param dateAndTime A string that contains either a "/" or a ":".
   * @return True if this string is a valid date or time, false otherwise.
   * @throws IllegalArgumentException If the components in dateAndTime are not numbers.
   * @throws InvalidParameterException If the numbers exceed expected values for each component.
   */
  static boolean isValidDateAndTime(String dateAndTime) {
    if (dateAndTime.contains("/")) { //Checking for the date
      int firstSlash = dateAndTime.indexOf("/");
      int secondSlash = dateAndTime.indexOf("/", firstSlash + 1);

      if (firstSlash > 0 && secondSlash > 0) {
        String month = dateAndTime.substring(0, firstSlash);
        String day = dateAndTime.substring(firstSlash + 1, secondSlash);
        String year = dateAndTime.substring(secondSlash + 1, dateAndTime.length());
        int months;
        int days;
        int years;

        if (checkForInt(month) && checkForInt(day) && (checkForInt(year) && year.length() == 4)) {
          months = Integer.parseInt(month);
          days = Integer.parseInt(day);
          years = Integer.parseInt(year);
          if (months <= 12 && days <= 31 && years > 0) {
            return true;
          } else {
            throw new InvalidParameterException("Date Was Incorrect. Was: " + dateAndTime + " Expected Format mm/dd/yyyy");
          }
        } else {
          throw new IllegalArgumentException("Date Was Invalid. Was: " + dateAndTime + " Example 09/16/2000");
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
          throw new InvalidParameterException("Time Was Incorrect. Was: " + dateAndTime + " Expected Format hh:mm");
        }
      } else {
        throw new IllegalArgumentException("Time Was Non Number. Was: " + dateAndTime + " Example 10:30");
      }
    }
    return false;
  }

  /**
   * Returns true if the given argument check can be turned into an int, and false otherwise.
   *
   * @param check A string that is being checked if it can be turned into an int
   * @return True if this string can be turned into an int, else false.
   */
  public static boolean checkForInt(String check) {
    try {
      Integer.parseInt(check);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

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
    String file = null;
    boolean hasFile = false;
    Airline airlineFromFile;

    if (args.length == 0) {
      System.err.println("It seems that you have not entered any information about the Airline or Flight Please retry again.\n" +
              "Format: java -jar target/airline-2023.0.0.jar [options] <args> \n " +
              "Options(Are optional)\n " +
              "*: -README -> Prints out the README \n " +
              "*: -print  -> Prints the description of the flight \n" +
              "Args: (IN THIS ORDER) \n" +
              "*: Airline Name -> Name of the airline being added \n" +
              "*: Flight Number-> The number of the Flight being added \n" +
              "*: Source       -> 3-letter code of departure airport \n" +
              "*: Departure date -> The date of departure(Formatted as mm/dd/yyyy)\n" +
              "*: Departure time -> The time of departure(Formatted as hh:mm \n" +
              "*: Destination ->  3-letter code of destination airport \n" +
              "*: Arrival date -> The date of arrival(Formatted as mm/dd/yyyy) \n" +
              "*: Arrival time -> The time of departure(Formatted as hh:mm \n " +
              "An example: java -jar target/airline-2023.0.0.jar -print JetBlue 100 abc 9/16/2023 10:30 def 9/16/2023 12:30\n");
    } else {

      for (int i = 0; i < args.length; i++) {
        if (args[i].contains("-")) {
          if (args[i].contains("-textFile")) {
            options++;
          }
          options++;
        }
      }

      String[] option = Arrays.copyOfRange(args, 0, options);
      String[] arguments = Arrays.copyOfRange(args, options, args.length);

      System.out.println("options " + Arrays.toString(option));
      System.out.println("Arguments " + Arrays.toString(arguments));

      if (args.length - options > 8) {
        System.err.println("The Number Of Arguments Has Exceeded The Limits");
        return;
      }

      if (arguments.length < 8) {
        System.err.println("Missing Command Line Argument");
        return;
      }

      for (String op : option) {
        if (op.equals("-print") || op.equals("-README") || op.equals("-textFile") || op.contains(".txt")) {
          if (op.equals("-print")) {
            hasPrint = true;
          }
          if (op.equals("-README")) {
            try {
              InputStream readme = Project1.class.getResourceAsStream("README.txt");
              BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
              String line;
              while ((line = reader.readLine()) != null) {
                System.out.println(line);
              }
              return;
            } catch (IOException e) {
              System.err.println("README Not Found");
              return;
            }
          }
          if (op.equals("-textFile")) {
            if (option[op.indexOf("-textFile") + 1].contains(".txt")) {
              file = option[op.indexOf("-textFile") + 1];
            } else {
              file = option[op.indexOf("-textFile") + 1] + ".txt";
              hasFile = true;
            }
          }
        }
        else {
          System.err.println(op + " option does not exist");
          return;
        }
      }

      name = arguments[0];

      if (checkForInt(arguments[1])) {
        flightNum = Integer.parseInt(arguments[1]);
      } else {
        System.err.println("Flight Number Entered Is Not A Number");
        return;
      }

      try {
        if (arguments[2].length() == 3) {
          if (checkValidCode(arguments[2])) {
            src = arguments[2];
          } else {
            System.err.println("The Source Code You Entered " + arguments[2] + " Contains Number. Code Can Not Have Number");
            return;
          }
        } else {
          System.err.println("The Source Code You Have Entered is " + arguments[2].length() + " Letters Long, Must Be Three");
          return;
        }
      } catch (Exception e) {

      }

      try {
        if(isValidDateAndTime(arguments[3]))
        {
          depart = arguments[3];
        }
        if(isValidDateAndTime(arguments[4]))
        {
          depart = depart + " " + arguments[4];
        }
      }catch (Exception e) {
        System.err.println("Departure " + e.getMessage());
        return;
      }

      if(arguments[5].length() == 3) {
        if(checkValidCode(arguments[5])) {
          dest = arguments [5];
        }
        else {
          System.err.println("The Destination Code You Entered " + arguments[5] + " Contains Number. Code Can Not Have Number");
          return;
        }
      }
      try {
        if(isValidDateAndTime(arguments[6]))
        {
          arrival = arguments[6];
        }
        if(isValidDateAndTime(arguments[7]))
        {
          arrival = arrival + " " + arguments[7];
        }
      }catch (Exception e) {
        System.err.println("Arrival " + e.getMessage());
        return;
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
        return;
      }

      if(hasFile) {
        File airlineFile = new File(file);


        //check if the file is empty of the name matches
        try {
          FileReader readFromFile = new FileReader(file);
          TextParser parseIntoObject = new TextParser(readFromFile);
          System.out.println(airline.getName());
          if(parseIntoObject.checkAirline(airline.getName()))
          {
            FileWriter fw = new FileWriter(airlineFile);
            TextDumper dumpIntoFile = new TextDumper(fw);
            dumpIntoFile.dump(airline);
          }
          else {
            System.err.println("File Does not contain the airline " + airline.getName());
            return;
          }
        }catch (Exception e) {

        }
        try{
          FileReader readFromFile = new FileReader(file);
          TextParser parseIntoObject = new TextParser(readFromFile);
          airlineFromFile = parseIntoObject.parse();
          Collection<Flight> Flights = airlineFromFile.getFlights();
          for (Flight flights : Flights) {
            System.out.println("Airline from file " + airline.getName() + ": " + flights.toString());
          }
        }catch (ParserException e) {
          System.err.println(e.getMessage());
        }catch (FileNotFoundException e) {

        }
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
