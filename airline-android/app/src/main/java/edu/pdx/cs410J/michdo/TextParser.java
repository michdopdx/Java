package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.security.InvalidParameterException;

/**
 * Class <code>TextParser</code> used to read Airline and its Flights from a file.
 * @author Michael Do
 */
public class TextParser implements AirlineParser<Airline> {
    private final Reader reader;
    /**
     * File reader which reads characters from text files.
     */


    /**
     * This constructor will create a new instance of <code>TextParser</code>
     * @param reader File Reader
     */
    public TextParser(Reader reader) {
        this.reader = reader;
    }

    /**
     * Will search file for given name, if the name is found or the file is empty return true.
     * @param name Name of the Airline
     * @return True if name is found or the file is empty, false otherwise.
     */
    public int checkAirline(String name) {
        try {
            BufferedReader br = new BufferedReader(this.reader);
            String line = br.readLine();
            if(br.readLine() == null) {
                return 1;
            }
            if(line.equals(name)) {
                return 2;
            }
        }catch (Exception e) {

        }
        return 0;
    }

    /**
     * Will parse a file and create and airline and its flights
     * @return A new Airline created from parsing file.
     * @throws ParserException Will be thrown if NO Airline was found.
     */
    @Override
    public Airline parse() throws ParserException,InvalidParameterException {
        String line;
        String airlineName = null;
        String flightNumber = null;
        String flightSrc = null;
        String flightDepart = null;
        String flightDest = null;
        String flightArrival = null;
        Airline airline = null;
        Flight flight = null;

        BufferedReader br = new BufferedReader(this.reader);
        try {
            while ((line = br.readLine()) != null) {
                if (!line.contains("|")) {
                    airlineName = line;
                    airline = new Airline(airlineName);
                }
                else if (line.contains("|")) {
                    String[] flightData = line.split("\\|");
                    flightNumber = flightData[0];
                    flightSrc = flightData[1];
                    flightDepart = flightData[2];
                    flightDest = flightData[3];
                    flightArrival = flightData[4];

                    try {
                        flight = new Flight(flightNumber, flightSrc, flightDepart, flightDest, flightArrival);
                        airline.addFlight(flight);
                    } catch (NullPointerException e) {
                        System.err.println(e.getMessage());
                    }
                    catch (InvalidParameterException e) {
                        throw new InvalidParameterException(e.getMessage());
                    }
                }
            }
            if(airlineName == null)
            {
                throw new ParserException("No AirLine was found");
            }
        } catch (InvalidParameterException e) {
            throw new ParserException("While parsing airline text" + e.getMessage());
        } catch (IOException e) {
        }
        return airline;
    }
}
