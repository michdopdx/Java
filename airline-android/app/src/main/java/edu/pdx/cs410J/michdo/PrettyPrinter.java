package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * Class <code>PrettyPrinter</code> used to Pretty print Airline and its Flights into file
 *
 * @author Michael Do
 */
public class PrettyPrinter implements AirlineDumper<Airline> {
    private final Writer writer;



    /**
     * Constructor for Pretty Printer
     * @param writer A Writer
     */
    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Takes in a date and uses DateFormat to then change the formatting of given date.
     * @param date The date.
     * @return A formatted string using DateFormat along with full date and short time.
     */
    public String prettyFormat (Date date) {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.SHORT, Locale.US);
        return format.format(date);
    }

    /**
     * Write content from an airline into a file using pretty print
     *
     * @param airline Airline object which will be stored into file
     */
    @Override

    public void dump(Airline airline) {
        Collection<Flight> listOfFlights = airline.getFlights();
        PrintWriter pw = new PrintWriter(this.writer);
        pw.println("Flights for AirLine: " + airline.getName());
        pw.println("******************************************");
        for (Flight flight : listOfFlights) {
            String srcCode = AirportNames.getName(flight.getSource());
            String destCode = AirportNames.getName(flight.getDestination());

            pw.println("Flight Number: " + flight.getNumber());
            pw.println("Departing Airport: " + flight.getSource() + " - " + srcCode);
            pw.println("Departing Data & Time: " + prettyFormat(flight.getDeparture()));
            pw.println("Arriving Airport: " + flight.getDestination() + " - " + destCode);
            pw.println("Arriving Data & Time: " + prettyFormat(flight.getArrival()));
            pw.println("Flight Duration: " +  flight.getFlightDuration() + " Minutes");
            pw.println("----------------------------------------");

        }
        pw.println("******************************************");
        pw.flush();
    }

    public void prettyFlight(Flight flight) {
        PrintWriter pw = new PrintWriter(this.writer);

        String srcCode = AirportNames.getName(flight.getSource());
        String destCode = AirportNames.getName(flight.getDestination());

        pw.println("Flight Number: " + flight.getNumber());
        pw.println("Departing Airport: " + flight.getSource() + " - " + srcCode);
        pw.println("Departing Data & Time: " + prettyFormat(flight.getDeparture()));
        pw.println("Arriving Airport: " + flight.getDestination() + " - " + destCode);
        pw.println("Arriving Data & Time: " + prettyFormat(flight.getArrival()));
        pw.println("Flight Duration: " +  flight.getFlightDuration() + " Minutes");

        pw.flush();
    }
}
