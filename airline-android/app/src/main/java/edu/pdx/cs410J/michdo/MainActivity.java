package edu.pdx.cs410J.michdo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.pdx.cs410J.michdo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrag(new HomeFragment());

        binding.navBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.home):
                    replaceFrag(new HomeFragment());
                    break;
                case (R.id.add):
                    replaceFrag(new AddFragment());
                    break;
                case(R.id.search):
                    replaceFrag(new SearchFragment());
                    break;
                case(R.id.help):
                    replaceFrag(new HelpFragment());
                    break;
            }
            return true;
        });
    }
    private void replaceFrag(Fragment frag) {
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.linearLayout,frag);
        fragTran.commit();
    }


}