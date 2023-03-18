package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.MissingFormatArgumentException;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    /**
     * Returns when there is a missing command line argument.
     */
    public static final String MISSING_ARGS = "Missing command line arguments";


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
     * Checks if a given string contains a number. If a number is present return false, else true.
     *
     * @param code Will search this string for numbers.
     * @return True if no number is fond in string else false.
     */
    public static boolean checkValidCode (String code)
    {
        if(code.length() == 3) {
            String check = AirportNames.getName(code);
            if (check != null) {
                return true;
            } else {
                return false;
            }
        }
        else return false;
    }


    /**
     * Main driver which runs the program
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String hostName = null;
        String portString = null;
        String airlineName = null;
        String flightNumber = null;
        String flightSrc = null;
        String flightDepart = null;
        String flightDest = null;
        String flightArrive= null;
        boolean hasHost = false;
        boolean hasPort = false;
        boolean hasSearch = false;
        boolean hasPrint = false;
        int options = 0;

        if(args.length == 0)
        {
            usage("No command line arguments");
            return;
        }

        for(int i = 0; i < args.length; i++) {
            String currentItem = args[i];
            if(currentItem.equals("-host"))
            {
                if(!args[i+1].contains("-")) {
                    hasHost = true;
                    hostName = args[i+1];
                    options++;
                }
                options++;
            } else if (currentItem.equals("-port")) {
                if(!args[i+1].contains("-")) {
                    hasPort = true;
                    portString = args[i+1];
                    options++;
                }
                options++;
            } else if (currentItem.equals("-search")) {
                hasSearch = true;
                options++;
            }else if (currentItem.equals("-print")) {
                hasPrint = true;
                options++;

            } else if (currentItem.equals("-README")) {
                try {
                    InputStream readme = Project5.class.getResourceAsStream("README.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println(line);
                    }
                    return;
                } catch (IOException e) {
                    System.err.println("README Not Found");
                    return;
                }
            }
        }
        String[] option = Arrays.copyOfRange(args, 0, options);
        String[] arguments = Arrays.copyOfRange(args, options, args.length);

        if(hasHost && hasPort )
        {
            if(arguments.length == 0) {
                usage("No Flight Arguments");
                return;
            }
        }
        else {
            if (hostName == null) {
                System.err.print("Missing Host");
                return;

            } else if ( portString == null) {
                System.err.print("Missing Port");
                return;
            }
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        if(hasSearch)
        {
            AirlineRestClient client = new AirlineRestClient(hostName, port);
            Airline airline = null;

                try{
                    if(arguments.length == 1) {
                        airlineName = arguments[0];
                        airline = client.getAirline(airlineName);
                    }
                    else if (arguments.length == 3) {
                        airlineName = arguments[0];
                        if(checkValidCode(arguments[1])) {
                            flightSrc = arguments[1];
                        }
                        else {
                            System.err.println("Invalid Source code from command line: " + arguments[1]);
                            return;
                        }
                        if(checkValidCode(arguments[2])) {
                            flightDest = arguments[2];
                        }
                        else {
                            System.err.println("Invalid Destination code from command line: " + arguments[2]);
                            return;
                        }
                        airline = client.getAirline(airlineName,flightSrc,flightDest);
                    }
                    else {
                        System.err.println("-Search Command does not support " + Arrays.toString(arguments));
                        return;
                    }

                } catch (ParserException e) {
                    System.err.println("ERROR parsing airline");
                    return;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    return;
                }
                catch (HttpRequestHelper.RestException e) {
                    System.err.println("Unable to search for flight with name: "+ airlineName+ " Src: "+ flightSrc +" Dest: " + flightDest);
                    return;
                }
                PrintWriter writer = new PrintWriter(System.out);
                PrettyPrinter prettyPrint =  new PrettyPrinter(writer);
                prettyPrint.dump(airline);
        }
        else
        {
            if(arguments.length < 10) {
                System.err.println("Missing Arguments: \n Arguments you entered: "+ Arrays.toString(arguments));
                return;
            }
            else if(arguments.length > 10){
                System.err.println("Extra Arguments: \n Arguments you entered: "+ Arrays.toString(arguments));
                return;
            }

            airlineName = arguments[0];

            flightNumber = arguments[1];

            if(checkValidCode(arguments[2]))
            {
                flightSrc = arguments[2];
            }
            else {
                System.err.println("Invalid Source code from command line: " + arguments[2]);
                return;
            }

            String date = arguments[3] + " " + arguments[4] + " " + arguments[5];
            if (formatDateAndTime(date)) {
                flightDepart = date;
            } else {
                System.err.println("Invalid Departure. Departure entered was, " + arguments[3] + " " + arguments[4] + " " + arguments[5] + " Expected Format mm/dd/yy hh:mm am/pm");
                return;
            }
            date = arguments[7] + " " + arguments[8] + " " + arguments[9];
            if (formatDateAndTime(date)) {
                flightArrive = date;
            } else {
                System.err.println("Invalid Arrival. Arrival entered was, " + arguments[7] + " " + arguments[8] + " " + arguments[9] + " Expected Format mm/dd/yy hh:mm am/pm");
                return;
            }

            if(checkValidCode(arguments[6]))
            {
                flightDest = arguments[6];
            }
            else {
                System.err.println("Invalid Destination code from command line: " + arguments[6]);
                return;
            }

            AirlineRestClient client = new AirlineRestClient(hostName, port);
            Airline airline;
            Flight flight;
            try {
                airline = new Airline(airlineName);
                flight = new Flight(flightNumber, flightSrc, flightDepart, flightDest, flightArrive);
                airline.addFlight(flight);
            }catch (NullPointerException e) {
                System.err.println(e.getMessage());
                return;
            } catch (InvalidParameterException e) {
                System.err.println(e.getMessage());
                return;
            } catch (MissingFormatArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }


            try {
                client.addFlight(airlineName,flightNumber,flightSrc,flightDepart,flightDest,flightArrive);

                Airline postedAirline = client.getAirline(airlineName);
                postedAirline.sortFlights();
                if(hasPrint) {
                    Collection<Flight> listOfFlights = airline.getFlights();
                    for (Flight flights : listOfFlights) {
                        System.out.println(airline.getName() + ": " + flights.toString());
                    }
                }
            }catch (IOException e) {
                System.err.println("Unable to Connect to Server");
            } catch (ParserException e) {
                System.err.println("Unable to parse Airlines");
            }
        }
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project5 host port [airlineName] [flightNumber] [flightSrc] [flightDepart] [flightDest] [flightArrive]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();
    }
}