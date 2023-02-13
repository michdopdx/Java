package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project2 {

  /**
   * Checks if the format of date is in pattern MM/dd/yyyy hh:mm a.
   * @param dateTime The date which is being checked for formatting.
   * @return True, is argument follow format, else false.
   */
  public static boolean formatDateAndTime(String dateTime)
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
      return;
    }
    for (int i = 0; i < args.length; i++) {
      if (args[i].contains("-") || args[i].contains(".txt")) {
        if (args[i].contains("-README") || args[i].contains("-print") || args[i].contains("-textFile") || (args[i].contains("-pretty")) || args[i].contains(".txt") || args[i].equals("-")) {
          if (args[i].contains("-README")) {
            try {
              InputStream readme = Project2.class.getResourceAsStream("README.txt");
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
          else if (args[i].contains("-print")) {
            options++;
          }

          else if (args[i].contains(".txt")) {
            options++;
          }

          else if (args[i].equals("-")) {
            options++;
          }
          else if (args[i].contains("-textFile")) {
            if (!args[i + 1].contains(".txt") && !args[i + 1].contains("-")) {
              file = args[i + 1] + ".txt";
              args[i + 1] = file;
            }
            else {
              file = args[i + 1];
            }
            options++;
          }
        }
      }
    }

    String[] option = Arrays.copyOfRange(args, 0, options);
    String[] arguments = Arrays.copyOfRange(args, options, args.length);


    if (args.length - options > 8) {
      System.err.println("Unknown command, The Number Of Arguments Has Exceeded The Limits");
      return;
    }
    if (arguments.length < 8) {
      System.err.println("Missing Command Line Argument");
      return;
    }

    for (String op : option) {
      if (op.equals("-print") || op.equals("-README") || op.equals("-textFile")  || op.equals("-pretty")|| op.contains(".txt")) {
        if (op.equals("-print")) {
          hasPrint = true;
        }
        if (op.equals("-textFile")) {
          hasFile = true;
        }
      }
      else {
        System.err.println(op + " option does not exist");
        return;
      }
    }
    name = arguments[0];

    String date = arguments[3] + " " + arguments[4];
    if(formatDateAndTime(date)) {
      depart = date;
    }
    else {
      System.err.println("Invalid Departure. Departure entered was, " + arguments[3] + " " + arguments[4] + " Expected Format mm/dd/yy hh:mm");
      return;
    }
    date = arguments[6] + " " + arguments[7];
    if(formatDateAndTime(date)){
      arrival = date;
    }
    else {
      System.err.println("Invalid Arrival. Arrival entered was, " + arguments[6] + " " + arguments[7] + " Expected Format mm/dd/yy hh:mm");
      return;
    }

    Airline airline;
    Flight flight;
      try {
        airline = new Airline(name);
        flight = new Flight(arguments[1], arguments[2], depart, arguments[5], arrival);
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
        try {
          if(airlineFile.getParentFile() != null) {
            airlineFile.getParentFile().mkdirs();
            airlineFile.createNewFile();
          }
          else
            airlineFile.createNewFile();
        }catch (IOException e) {
          System.err.println("Input error when creating " + file);
          return;
        }
        if (airlineFile != null) {
          try {
            FileReader readFromFile = new FileReader(airlineFile);
            TextParser parseIntoObject = new TextParser(readFromFile);
            int value = parseIntoObject.checkAirline(airline.getName());
            if (value == 1) {
              FileWriter fw = new FileWriter(airlineFile);
              TextDumper dumpIntoFile = new TextDumper(fw);
              dumpIntoFile.dump(airline);
            } else if (value == 2) {
              FileWriter fw = new FileWriter(airlineFile, true);
              TextDumper appendToFile = new TextDumper(fw);
              appendToFile.appendFlightToFile(airline);
            } else {
              System.err.println("File Does not contain the airline " + airline.getName());
              return;
            }
          } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
          }

          try {
            FileReader readFromFile = new FileReader(airlineFile);
            TextParser parseIntoObject = new TextParser(readFromFile);
            airlineFromFile = parseIntoObject.parse();
            try {
              FileWriter softFw = new FileWriter(airlineFile);
              TextDumper dumpIntoFile = new TextDumper(softFw);
              dumpIntoFile.dump(airlineFromFile);
            } catch (IOException e) {
              System.err.println("File, " + airlineFile + " Doesn't exist");
              return;
            }
          } catch (InvalidParameterException e) {
            System.err.println(e.getMessage());
            return;
          } catch (ParserException e) {
            System.err.println(e.getMessage());
            return;
          } catch (FileNotFoundException e) {
            System.err.println("File " + file + "Not Found");
            return;
          }
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
