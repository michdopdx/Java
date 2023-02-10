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
public class Project3 {
  /**
   * Checks if the format of date is in pattern MM/dd/yyyy hh:mm a.
   * @param dateTime The date which is being checked for formatting.
   * @return True, is argument follow format, else false.
   */
  public static boolean formatDateAndTime(String dateTime)
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
   * The main driver for Airline Application
   * @param args Command Line Arguments.
   */
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
    String prettyFile = null;
    boolean hasFile = false;
    boolean hasPretty = false;
    boolean hasPrintPretty = false;
    Airline airlineFromFile = null;


    if (args.length == 0) {
      System.err.println("It seems that you have not entered any information about the Airline or Flight Please retry again.\n" +
              "Format: java -jar target/airline-2023.0.0.jar [options] <args> \n " +
              "Options(Are optional)\n " +
              "*: -README -> Prints out the README \n " +
              "*: -print  -> Prints the description of the flight \n" +
              "*: -pretty file -  -> Prints the description of the flight in pretty format \n" +
              "*: -pretty file  -> Stores flight into specified file \n" +
              "Args: (IN THIS ORDER) \n" +
              "*: Airline Name -> Name of the airline being added \n" +
              "*: Flight Number-> The number of the Flight being added \n" +
              "*: Source       -> 3-letter code of departure airport \n" +
              "*: Departure date -> The date of departure(Formatted as mm/dd/yyyy)\n" +
              "*: Departure time -> The time of departure(Formatted as hh:mm \n" +
              "*: Departure AM/PM -> The time of departure(am / pm \n" +
              "*: Destination ->  3-letter code of destination airport \n" +
              "*: Arrival date -> The date of arrival(Formatted as mm/dd/yyyy) \n" +
              "*: Arrival time -> The time of departure(Formatted as hh:mm \n " +
              "*: Arrival AM/PM -> The time of Arrival(am / pm \n" +
              "An example: java -jar target/airline-2023.0.0.jar -print JetBlue 100 PDX 9/16/2023 10:30 am PDX 9/16/2023 12:30 pm\n");
      return;
    }

      for (int i = 0; i < args.length; i++) {
        if (args[i].contains("-") || args[i].contains(".txt")) {
          if(args[i].contains("-README") || args[i].contains("-print") || args[i].contains("-textFile") || (args[i].contains("-pretty")) || args[i].contains(".txt"))
          {
            if (args[i].contains("-README")) {
              try {
                InputStream readme = Project3.class.getResourceAsStream("README.txt");
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

            if (args[i].contains("-print")) {
              options++;
            }

            if (args[i].contains("-textFile")) {
              if (!args[i + 1].contains(".txt") && !args[i + 1].contains("-")) {
                file = args[i + 1] + ".txt";
                args[i + 1] = file;
              } else if (args[i + 1].contains("-")) {
                System.err.println("Missing TextFile for -textFile");
                return;
              } else {
                file = args[i + 1];
              }
              options++;
            }

            if (args[i].contains("-pretty")) {
              if (!args[i + 1].contains(".txt") && !args[i + 1].equals("-")) {
                prettyFile = args[i + 1] + ".txt";
                args[i + 1] = prettyFile;
              } else if (args[i + 1].equals("-")) {
              } else {
                prettyFile = args[i + 1];
              }
              options++;
            }

            if (args[i].contains(".txt")) {
              options++;
            }
          }
          else
            options ++;
        }
      }

      String[] option = Arrays.copyOfRange(args, 0, options);
      //System.out.println(Arrays.toString(option));
      String[] arguments = Arrays.copyOfRange(args, options, args.length);

    if (args.length - options > 10) {
        System.err.println("The Number Of Arguments Has Exceeded The Limits");
        return;
      }
      if (arguments.length < 10) {
        System.err.println("Missing Command Line Argument");
        return;
      }

      for (String op : option) {
        if (op.equals("-print") || op.equals("-README") || op.equals("-textFile")  || op.equals("-pretty")|| op.contains(".txt") || op.equals("-") ||op.equals(prettyFile)) {
          if (op.equals("-print")) {
            hasPrint = true;
          }
          if (op.equals("-textFile")) {
            hasFile = true;
          }
          if (op.equals("-pretty")) {
            hasPretty = true;
          }
          if(op.equals("-")) {
            hasPrintPretty = true;
          }
        }
        else {
          System.err.println(op + " option does not exist");
          return;
        }
      }
      name = arguments[0];


    String date = arguments[3] + " " + arguments[4] + " " + arguments[5];
    if(formatDateAndTime(date)) {
      depart = date;
    }
    else {
        System.err.println("Invalid Departure. Departure entered was, " + arguments[3] + " " + arguments[4] +  " " + arguments[5] + " Expected Format mm/dd/yy hh:mm am/pm");
        return;
    }
    date = arguments[7] + " " + arguments[8]+ " " + arguments[9];
    if(formatDateAndTime(date)){
        arrival = date;
    }
    else {
      System.err.println("Invalid Arrival. Arrival entered was, " + arguments[7] + " " + arguments[8] + " " + arguments[9] + " Expected Format mm/dd/yy hh:mm am/pm");
      return;
    }


    Airline airline;
    Flight flight;
    try {
      airline = new Airline(name);
      flight = new Flight(arguments[1], arguments[2], depart, arguments[6], arrival);
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
        airlineFile.createNewFile();
        FileReader readFromFile = new FileReader(file);
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
        FileReader readFromFile = new FileReader(file);
        TextParser parseIntoObject = new TextParser(readFromFile);
        airlineFromFile = parseIntoObject.parse();
        airlineFromFile.sortFlights();
        try {
          FileWriter softFw = new FileWriter(airlineFile);
          TextDumper dumpIntoFile = new TextDumper(softFw);
          dumpIntoFile.dump(airlineFromFile);
        }catch (IOException e) {
          System.err.println("File, " + airlineFile +" Doesn't exist");
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

    if(hasPretty) {
      if(hasPrintPretty) {
        PrintWriter writer = new PrintWriter(System.out);
        PrettyPrinter pretty = new PrettyPrinter(writer);
        if(hasFile) {
          pretty.dump(airlineFromFile);
        }
        else {
          pretty.dump(airline);
        }
      }

      else if(prettyFile != null) {
        Airline prettyChoice;
        if(hasFile) {
          prettyChoice = airlineFromFile;
        }
        else
          prettyChoice = airline;

        try {
          File pretty = new File(prettyFile);
          FileWriter fw = new FileWriter(pretty);
          PrettyPrinter printPretty = new PrettyPrinter(fw);
          printPretty.dump(prettyChoice);
        } catch (IOException e) {
          System.err.println("No able to write to " + prettyFile);
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
