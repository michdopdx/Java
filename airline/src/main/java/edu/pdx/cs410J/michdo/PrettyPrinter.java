package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.IOException;
import java.io.PrintWriter;
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
		 * File writer which writes characters to text files.
		 */

		/**
		 * This constructor creates an instance of <code>TextDumper</code>
		 *
		 * @param writer File writer which writes characters to text files.
		 */
		public PrettyPrinter(Writer writer) {
				this.writer = writer;
		}


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
						for (Flight flight : listOfFlights) {
								String srcCode = AirportNames.getName(flight.getSource());
								String destCode = AirportNames.getName(flight.getDestination());

								if(srcCode == null)
								{
										srcCode = flight.getSource();
								}
								if(destCode == null)
								{
										destCode = flight.getDestination();
								}
								pw.println(airline.getName() +":" + " Flight " +
												flight.getNumber() + " One Way Trip from " +
												srcCode + " To " +
												destCode + " Flight Is Scheduled To Depart From " +
												srcCode + " Airport \nOn " +
												prettyFormat(flight.getDeparture()) + ", And Arriving At " +
												destCode + " Airport at " +
												prettyFormat(flight.getArrival())+"." + " The Duration Of The Flight is " +  flight.getFlightDuration() + " Minutes \n");
						}
						pw.flush();
		}
}
