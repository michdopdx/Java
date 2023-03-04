package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.Writer;
import java.util.Collection;

/**
 * Creates a dumper object which allows for writing to a xml file.
 * @author Michael Do
 */
public class XmlDumper implements AirlineDumper<Airline> {

		/**
		 * File field used to define a file to dump to.
		 */
		private final Writer write;


		/**
		 * Constructor to create a XmlDumper object.
		 * @param file Specified file where to dump.
		 */
		public XmlDumper(Writer writer) {
				this.write = writer;
		}

		/**
		 * Will write a given airline and all of its flights into a xml file according to DTD
		 * @param airline Airline which contains flights that will be dumped.
		 * @throws RuntimeException When an error occurs while dumping
		 *
		 */
		@Override
		public void dump(Airline airline){

				try {
						AirlineXmlHelper helper = new AirlineXmlHelper();
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
						DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);
            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType doctype = dom.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            Document workingDoc = dom.createDocument(null, "airline", doctype);

						Element root = workingDoc.getDocumentElement();
						Element airlineName = workingDoc.createElement("name");
						root.appendChild(airlineName);
						airlineName.appendChild(workingDoc.createTextNode(airline.getName()));

						Collection<Flight> listOfFlights = airline.getFlights();
						for(Flight flight:listOfFlights) {
								Element f = workingDoc.createElement("flight");
								root.appendChild(f);

								Element num =  workingDoc.createElement("number");
								f.appendChild(num);
								num.appendChild(workingDoc.createTextNode(String.valueOf(flight.getNumber())));

								Element src = workingDoc.createElement("src");
								f.appendChild(src);
								String source = flight.getSource();
								src.appendChild(workingDoc.createTextNode(source));

								Element depart = workingDoc.createElement("depart");
								f.appendChild(depart);
								String [] departureComp = flight.getDepartureDateComponent().clone();

								Element departDate = workingDoc.createElement("date");
								depart.appendChild(departDate);
								departDate.setAttribute("day",departureComp[1]);
								departDate.setAttribute("month",departureComp[0]);
								departDate.setAttribute("year",departureComp[2]);

								Element departTime = workingDoc.createElement("time");
								depart.appendChild(departTime);
								departTime.setAttribute("hour",departureComp[3]);
								departTime.setAttribute("minute",departureComp[4]);

								Element dest = workingDoc.createElement("dest");
								f.appendChild(dest);
								dest.appendChild(workingDoc.createTextNode(flight.getDestination()));

								Element arrival = workingDoc.createElement("arrive");
								f.appendChild(arrival);
								String [] arrivalComp = flight.getArrivalDateComponent().clone();

								Element arrivalDate = workingDoc.createElement("date");
								arrival.appendChild(arrivalDate);
								arrivalDate.setAttribute("day",arrivalComp[1]);
								arrivalDate.setAttribute("month",arrivalComp[0]);
								arrivalDate.setAttribute("year",arrivalComp[2]);

								Element arrivalTime = workingDoc.createElement("time");
								arrival.appendChild(arrivalTime);
								arrivalTime.setAttribute("hour",arrivalComp[3]);
								arrivalTime.setAttribute("minute",arrivalComp[4]);
						}

						TransformerFactory transform = TransformerFactory.newInstance();
						Transformer xmlTrans = transform.newTransformer();
						xmlTrans.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");

						DOMSource docSource = new DOMSource(workingDoc);
						StreamResult xmlResult = new StreamResult(this.write);
						xmlTrans.setOutputProperty(OutputKeys.INDENT, "yes");

						xmlTrans.transform(docSource,xmlResult);


				} catch (TransformerConfigurationException e) {
						throw new RuntimeException(e);
				} catch (TransformerException e) {
						throw new RuntimeException(e);
				} catch (ParserConfigurationException e) {
						throw new RuntimeException(e);
				}
		}
}
