package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AbstractAirline;
import org.checkerframework.checker.units.qual.A;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Class representation of an Airline.
 * @author Michael Do
 */
public class Airline extends AbstractAirline<Flight> {
  private final String name;
  /**
   * The name of a Flight.
   */
  Collection <Flight> listOfFlights = new ArrayList<Flight>();
  /**
   * Data representation of an Airline
   */

  /**
   * Airline constructor which creates an Airline object with a given name.
   *
   * @param name Is a string that will name the airline
   * @throws NullPointerException If String is null.
   */
  public Airline(String name) {
    if(name == null) {
      throw new NullPointerException("Name of airline is invalid or has not been entered.");
    }
    this.name = name;
  }

  /**
   * Gets the name of a given Airline Object.
   * @return Then name of the Airline.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Adds a given flight passed as an argument to a collect of flights for an airline.
   *
   * @param flight Is a populated flight object.
   */
  @Override
  public void addFlight(Flight flight) {
    listOfFlights.add(flight);
  }

  /**
   * Will return the collection of flights.
   *
   * @return the collection of flights for a specific airline.
   */
  @Override
  public Collection<Flight> getFlights() {
    return listOfFlights;
  }

  /**
   * Sorts the collection of flights based on source code and departure time.
   */
  public void sortFlights() {
    ArrayList<Flight> sortList = new ArrayList<>(this.listOfFlights);
    try {
      Collections.sort(sortList);
      this.listOfFlights = new ArrayList<>();
      for (Flight f : sortList) {
        this.listOfFlights.add(f);
      }
    }catch (ClassCastException e)
    {

    }catch (UnsupportedOperationException e)
    {

    }
  }
}
