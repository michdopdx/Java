********************************
Project One Airline Application
            README
          Michael Do
********************************
Project Description:
================================
This project creates an application
that allows users to quickly create
a collection of Airlines, and their
flights. This service will be useful
for those who need a quick and easy
way to manage and list flights
for airlines. This service will
accept command line arguments and loop
through the arguments and parse each of
the arguments based on else-if checks.
After all arguments have been parsed
An airline object will be created as
well as a Flight. Once the flight object
has been populated, the flight will then
be added to a collection of flights for the
specified airline.
=================================
How To Use:
=================================
To run this application enter this into the command line
*java -jar target/airline-2023.0.0.jar [options] <args>

Options:(can be in any order)
* -README
* -print
Args: (Must be in this order)
* Airline Name
* Flight Number
* Source - > Three-letter code of departure airport.
* Departure -> Date(mm/dd/yyyy) and Time(hh:mm) of departure.
* Destination -> Three-letter code of arrival airport.
* Arrival -> Date(mm/dd/yyyy) and Time(hh:mm) of departure

Example: java -jar target/airline-2023.0.0.jar -print JetBlue 100 abc 9/16/2023 10:30 def 9/16/2023 12:30
************************************
