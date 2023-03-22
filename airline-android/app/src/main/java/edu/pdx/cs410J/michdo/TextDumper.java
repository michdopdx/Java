
package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Class <code>TextDumper</code> used to write Airline and its Flights into file
 *
 * @author Michael Do
 */
public class TextDumper implements AirlineDumper<Airline> {
    private final Writer writer;
    /**
     * File writer which writes characters to text files.
     */

    /**
     * This constructor creates an instance of <code>TextDumper</code>
     * @param writer File writer which writes characters to text files.
     */
    public TextDumper(Writer writer) {
        this.writer = writer;
    }


    /**
     * Changes variable of type Data to type String
     * @param date A date for type Date
     * @return A string date format as MM/dd/yyyy hh:mm a
     */
    public String dateToString(Date date)
    {
        String stringDate;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        stringDate = format.format(date);
        return stringDate;
    }

    /**
     * This will append flight to a given file.
     *
     * @param airline  Airline object which we want to append to file containing same Airline
     */
    public void appendFlightToFile(Airline airline) throws IOException{
        Collection<Flight> listOfFlights = airline.getFlights();

        PrintWriter pw = new PrintWriter(this.writer);
        for(Flight flight:listOfFlights) {
            pw.println(flight.getNumber() + "|" +
                    flight.getSource() + "|" +
                    dateToString(flight.getDeparture()) + "|" +
                    flight.getDestination() + "|" +
                    dateToString(flight.getArrival()));
            this.writer.flush();
        }
    }

    /**
     * Writes contents from a given airline into a file
     *
     * @param airline Airline object which will be stored into file.
     */
    @Override
    public void dump(Airline airline) throws IOException {
        Collection<Flight> listOfFlights = airline.getFlights();
        PrintWriter pw = new PrintWriter(this.writer);

        pw.println(airline.getName());
        for(Flight flight:listOfFlights) {
            pw.println(flight.getNumber() + "|" +
                    flight.getSource() + "|" +
                    dateToString(flight.getDeparture()) + "|" +
                    flight.getDestination() + "|" +
                    dateToString(flight.getArrival()));
        }
        pw.flush();
    }
}


















//package edu.pdx.cs410J.michdo;
//
//import edu.pdx.cs410J.AirlineDumper;
//
//import java.io.*;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Collection;
//import java.util.Date;
//
///**
// * Class <code>TextDumper</code> used to write Airline and its Flights into file
// *
// * @author Michael Do
// */
//public class TextDumper implements AirlineDumper<Airline> {
//    private final FileOutputStream fos;
//    /**
//     * File writer which writes characters to text files.
//     */
//
//    /**
//     * This constructor creates an instance of <code>TextDumper</code>
//     * @param fileOutputStream File writer which writes characters to text files.
//     */
//    public TextDumper(FileOutputStream fileOutputStream) {
//        this.fos = fileOutputStream;
//    }
//
//
//    /**
//     * Changes variable of type Data to type String
//     * @param date A date for type Date
//     * @return A string date format as MM/dd/yyyy hh:mm a
//     */
//    public String dateToString(Date date)
//    {
//        String stringDate;
//        DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
//        stringDate = format.format(date);
//        return stringDate;
//    }
//
////    /**
////     * This will append flight to a given file.
////     *
////     * @param airline  Airline object which we want to append to file containing same Airline
////     */
////    public void appendFlightToFile(Airline airline) throws IOException{
////        Collection<Flight> listOfFlights = airline.getFlights();
////
////        PrintWriter pw = new PrintWriter(this.writer);
////        for(Flight flight:listOfFlights) {
////            pw.println(flight.getNumber() + "|" +
////                    flight.getSource() + "|" +
////                    dateToString(flight.getDeparture()) + "|" +
////                    flight.getDestination() + "|" +
////                    dateToString(flight.getArrival()));
////            this.writer.flush();
////        }
////    }
//
//    /**
//     * Writes contents from a given airline into a file
//     *
//     * @param airline Airline object which will be stored into file.
//     */
//    @Override
//    public void dump(Airline airline) throws IOException {
//        Collection<Flight> listOfFlights = airline.getFlights();
//
//        this.fos.write(airline.getName().getBytes());
//        for(Flight flight:listOfFlights) {
//            String write = flight.getNumber() + "|" +
//                    flight.getSource() + "|" +
//                    dateToString(flight.getDeparture()) + "|" +
//                    flight.getDestination() + "|" +
//                    dateToString(flight.getArrival());
//            this.fos.write(write.getBytes());
//            this.fos.close();
//        }
//
////        PrintWriter pw = new PrintWriter(this.writer);
////
////        pw.println(airline.getName());
////        for(Flight flight:listOfFlights) {
////            pw.println(flight.getNumber() + "|" +
////                    flight.getSource() + "|" +
////                    dateToString(flight.getDeparture()) + "|" +
////                    flight.getDestination() + "|" +
////                    dateToString(flight.getArrival()));
////        }
////        pw.flush();
//    }
//}
