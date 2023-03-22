package edu.pdx.cs410J.michdo;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.MissingFormatArgumentException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add, container, false);
        Button button  = (Button) view.findViewById(R.id.Add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String airlineName = null;
                String flightNum = null;
                String flightSrc = null;
                String flightDepart = null;
                String flightDest = null;
                String flightArrival = null;
                String date = null;
                String time = null;

                EditText inputAirlineName = (EditText) view.findViewById(R.id.airlineName);
                EditText inputFlightNumber = (EditText) view.findViewById(R.id.flightNumber);
                EditText inputFlightSrc = (EditText) view.findViewById(R.id.flightSrc);
                EditText inputFlightDepartDate = (EditText) view.findViewById(R.id.flightDepartureDate);
                EditText inputFlightDepartureTime = (EditText) view.findViewById(R.id.flightDepartureTime);
                EditText inputFlightDest = (EditText) view.findViewById(R.id.flightDestinationCode);
                EditText inputFlightArrivalDate = (EditText) view.findViewById(R.id.flightArrivalDate);
                EditText inputFlightArrivalTime = (EditText) view.findViewById(R.id.flightArrivalTime);

                airlineName = inputAirlineName.getText().toString();
                flightNum = inputFlightNumber.getText().toString();
                flightSrc = inputFlightSrc.getText().toString();
                flightDepart = inputFlightDepartDate.getText().toString() + " " + inputFlightDepartureTime.getText().toString();
                flightDest = inputFlightDest.getText().toString();
                flightArrival =  inputFlightArrivalDate.getText().toString() + " " + inputFlightArrivalTime.getText().toString();

                Airline airline;
                Flight flight;
                File airlineFile = null;
                try {
                    airline = new Airline(airlineName);
                    flight = new Flight(flightNum, flightSrc, flightDepart, flightDest, flightArrival);
                    airline.addFlight(flight);

                }catch (NullPointerException e) {
                    Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                } catch (InvalidParameterException e) {
                    Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                return;
                } catch (MissingFormatArgumentException e) {
                    Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                return;
                }

                airlineFile= new File(getActivity().getApplicationContext().getFilesDir(),airlineName+".txt");
                try {
                    if(airlineFile.exists())
                    {
                        FileWriter fw = new FileWriter(airlineFile, true);
                        TextDumper dumper = new TextDumper(fw);
                        dumper.appendFlightToFile(airline);
                    }
                    else {
                        FileWriter fw = new FileWriter(airlineFile, true);
                        TextDumper dumper = new TextDumper(fw);
                        dumper.dump(airline);
                    }

                    } catch (FileNotFoundException e) {
                        Toast.makeText(getActivity().getApplicationContext(),"FIle is missing",Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e) {
                        Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                Toast.makeText(getActivity().getApplicationContext(),"Airline And Flight Added",Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }
}
