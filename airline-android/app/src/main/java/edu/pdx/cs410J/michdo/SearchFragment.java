package edu.pdx.cs410J.michdo;

import android.annotation.SuppressLint;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ListView viewListFlights = view.findViewById(R.id.list);
        String[] empty = {"empty"};
        ArrayAdapter<String> start = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, empty);
        viewListFlights.setAdapter(start);


        Button button = (Button) view.findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String airlineName = null;
                String srcCode = null;
                String destCode = null;
                String searchAirlineName = null;
                Airline airlineFromStorage;
                EditText inputName = (EditText) view.findViewById(R.id.searchAirlineName);
                EditText inputSrc = (EditText) view.findViewById(R.id.inputSrc);
                EditText inputDest = (EditText) view.findViewById(R.id.inputDest);

                airlineName = inputName.getText().toString();


                srcCode = inputSrc.getText().toString();
                destCode = inputDest.getText().toString();

                if(airlineName.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "missing Airline Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(srcCode.isEmpty() && !destCode.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "missing Source Code", Toast.LENGTH_SHORT).show();
                    return;
                }else if(destCode.isEmpty() && !srcCode.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "missing Destination Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!srcCode.isEmpty() && !destCode.isEmpty()) {
                    if (!checkValidCode(srcCode)) {
                        Toast.makeText(getActivity().getApplicationContext(), srcCode + " Invalid Source Code", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!checkValidCode(destCode)) {
                        Toast.makeText(getActivity().getApplicationContext(), destCode + " Invalid Destination Code", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                searchAirlineName = airlineName + ".txt";

                boolean hasAirline = false;
                String[] files = getActivity().getApplicationContext().fileList();
                for (String file : files) {
                    if (file.equals(searchAirlineName)) {
                        hasAirline = true;
                    }
                }

                if (hasAirline) {
                    try {
                        File airlineFile = new File(getActivity().getApplicationContext().getFilesDir(), searchAirlineName);

                        FileReader readFromFile = new FileReader(airlineFile);  //use to be file
                        TextParser parser = new TextParser(readFromFile);
                        airlineFromStorage = parser.parse();

                        Collection<Flight> listOfFlights = airlineFromStorage.getFlights();
                        ArrayList<String> flightList = new ArrayList<String>();

                        for (Flight flight : listOfFlights) {
                            StringWriter prettyString = new StringWriter();
                            PrintWriter writer = new PrintWriter(prettyString);
                            PrettyPrinter prettyPrint = new PrettyPrinter(writer);
                            if(!srcCode.isEmpty() && !destCode.isEmpty()) {
                                if(flight.getSource().equals(srcCode) && flight.getDestination().equals(destCode)) {
                                    prettyPrint.prettyFlight(flight);
                                    flightList.add(prettyString.toString());
                                }
                            }
                            else {
                            prettyPrint.prettyFlight(flight);
                            flightList.add(prettyString.toString());
                            }
                        }
                        if(flightList.isEmpty() & !srcCode.isEmpty() && !destCode.isEmpty()) {
                            String[] noResult = {"No Results for Source:  " + srcCode + " Destination: "+ destCode };
                            ArrayAdapter<String> result = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, noResult);
                            viewListFlights.setAdapter(result);

                        }
                        else {
                            ArrayAdapter<String> prettyStringFlight = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, flightList);
                            viewListFlights.setAdapter(prettyStringFlight);
                        }

                    } catch (FileNotFoundException e) {
                        Toast.makeText(getActivity().getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                    } catch (ParserException e) {
                        throw new RuntimeException(e);
                    }
                } else{
                    String[] noResult = {"No Results for: " + airlineName};
                    ArrayAdapter<String> result = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, noResult);
                    viewListFlights.setAdapter(result);
                }
            }
        });
        return view;
    }


    /**
     * Checks if a given string contains a number. If a number is present return false, else true.
     *
     * @param code Will search this string for numbers.
     * @return True if no number is fond in string else false.
     */
    public static boolean checkValidCode(String code) {
        String check = AirportNames.getName(code);
        if (check != null) {
            return true;
        } else {
            return false;
        }
    }
}


