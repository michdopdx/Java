package edu.pdx.cs410J.michdo;

import edu.pdx.cs410J.AbstractAirline;
import org.checkerframework.checker.units.qual.A;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  Collection <Flight> listOfFlights = new ArrayList<Flight>();

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

  @Override
  public void addFlight(Flight flight) {
    listOfFlights.add(flight);
    if(listOfFlights == null)
    {
      System.out.println("buh");
    }
  }

  @Override
  public Collection<Flight> getFlights() {
    return listOfFlights;
  }
}
