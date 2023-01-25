package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AbstractAirline;
import org.checkerframework.checker.units.qual.A;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  Collection <Flight> listOfFlights = new ArrayList<Flight>();

  /**
   * Airline Constructor: Creates an Airline object with a given name
   *
   *
   * @param name
   */
  public Airline(String name) {
    if(name == null) {
      throw new NullPointerException("Name of airline is invalid or has not been entered.");
    }
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Adds a given flight passed as an argument to a collect of flights for an airline.
   * @param flight Is a populated flight object.
   */
  @Override
  public void addFlight(Flight flight) {
    listOfFlights.add(flight);
  }

  /**
   * @return the collection of flights for a specific airline.
   */
  @Override
  public Collection<Flight> getFlights() {
    return listOfFlights;
  }
}
