package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a parser object which allows for parsing a xml file.
 * @author Michael Do
 */
public class XmlParser implements AirlineParser<Airline> {


		/**
		 * File field used to define a file to read from.
		 */
		private final File file;

		/**
		 * Constructor to create a XmlParser object.
		 * @param file Specified file where to read from.
		 */
		public XmlParser(File file) {
				this.file = file;
		}


		/**
		 * Will scan a given XML file for a given name
		 * @param name Is the name to scan for.
		 * @return 1 if the name is found, else 0
		 * @throws IOException When the file is access.
		 */
		public  int checkXmlAirline(String name) throws IOException {
				Document doc;
				try{
						AirlineXmlHelper helper = new AirlineXmlHelper();
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						factory.setValidating(true);
						DocumentBuilder builder = factory.newDocumentBuilder();
						builder.setErrorHandler(helper);
						builder.setEntityResolver(helper);
						doc = builder.parse(this.file);

						doc.getDocumentElement().getNodeName();
						String airlineName = doc.getElementsByTagName("name").item(0).getTextContent();
						if(name.equals(airlineName))
						{
								return 1;
						}

				} catch (ParserConfigurationException e) {
						throw new RuntimeException(e);
				} catch (IOException e) {
						throw new IOException("File " + this.file + " does not exist");
				} catch (SAXException e) {
						throw new RuntimeException(e);
				}
				return 0;
		}

		/**
		 * Used to format the date and time from the date and time attributes from xml
		 * @param list lit of depart or arrives
		 * @return A String formatted date.
		 * @throws ParseException When not able to parse the elements
		 */
		public String formatXmlDate(NodeList list) throws ParseException {
				String date = null;
				String time = null;
				String day = null;
				String month = null;
				String year = null;
				String hour;
				String min;

				Node n = list.item(0);
				Element element = (Element) n;
				NodeList items = element.getChildNodes();

				for(int i = 0; i < items.getLength(); i++) {
						Node item = items.item(i);
						if (item.getNodeType() == Node.ELEMENT_NODE) {
								Element parsedItem = (Element) item;
								switch (parsedItem.getNodeName()) {
										case "date": {
												day = parsedItem.getAttribute("day");
												month = parsedItem.getAttribute("month");
												year = parsedItem.getAttribute("year");
												date = month + "/" + day + "/" + year;
												break;
										}
										case "time": {
												hour = parsedItem.getAttribute("hour");
												min = parsedItem.getAttribute("minute");
												time = hour + ":" + min;
												break;
										}
								}
						}
				}
				Date temp = null;
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
				temp = format.parse(date + " " + time);
				SimpleDateFormat correct = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				String correctDate = correct.format(temp);

				return correctDate;
		}

		/**
		 * Used to parse a given xml file containing airline and flights, and create a new airline
		 * @return The airline from xml file
		 * @throws ParserException When Not able to parse an element.
		 */
		@Override
		public Airline parse() throws ParserException {

				String airlineName = null;
				String flightNum = null;
				String source = null;
				String depart = null;
				String dest = null;
				String arrive = null;
				Flight flight = null;
				Document doc =null;

				try{
						AirlineXmlHelper helper = new AirlineXmlHelper();
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						factory.setValidating(true);
						DocumentBuilder builder = factory.newDocumentBuilder();
						builder.setErrorHandler(helper);
						builder.setEntityResolver(helper);
						doc = builder.parse(this.file);

				} catch (ParserConfigurationException | IOException e) {
						throw new RuntimeException(e);
				}catch (SAXException e) {

						throw new RuntimeException(e);
				}

				doc.getDocumentElement().getNodeName();
				airlineName = doc.getElementsByTagName("name").item(0).getTextContent();

				Airline airline = new Airline(airlineName);

				NodeList airlineFlightList = doc.getElementsByTagName("flight");
				for(int i = 0; i < airlineFlightList.getLength(); i++) {
						Node singleFlight = airlineFlightList.item(i);

						if(singleFlight.getNodeType() == Node.ELEMENT_NODE) {
								Element ele = (Element) singleFlight;

								flightNum = ele.getElementsByTagName("number").item(0).getTextContent();
								source = ele.getElementsByTagName("src").item(0).getTextContent();
								dest = ele.getElementsByTagName("dest").item(0).getTextContent();

								NodeList departList = ele.getElementsByTagName("depart");
								try {
										depart = formatXmlDate(departList);
								} catch (ParseException e) {
										throw new RuntimeException(e);
								}
								NodeList arrivalList = ele.getElementsByTagName("arrive");
								try {
										arrive = formatXmlDate(arrivalList);
								} catch (ParseException e) {
										throw new RuntimeException(e);
								}
						}
						try {
								flight = new Flight(flightNum, source, depart, dest, arrive);
								airline.addFlight(flight);
						} catch (NullPointerException e) {
								throw new NullPointerException(e.getMessage());
						}
						catch (InvalidParameterException e) {
								throw new InvalidParameterException(e.getMessage());
						}

				}
				return airline;
		}
}
