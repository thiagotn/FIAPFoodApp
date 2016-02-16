package cc.thiago.fiapfoodapp.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.view.activity.LoginActivity;
import cc.thiago.fiapfoodapp.view.activity.MainActivity;

public class MainFragment extends Fragment {

    Button addRestaurantButton;
    Button listRestaurantButton;
    Button viewOnMapsButton;
    Button aboutButton;
    Button logoutButton;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        addRestaurantButton = (Button) rootView.findViewById(R.id.btAddRestaurant);
        addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRestaurantFragment fragment = new AddRestaurantFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment); // FIX
                fragmentTransaction.commit();
            }
        });

        listRestaurantButton = (Button) rootView.findViewById(R.id.btListRestaurants);
        listRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantsFragment fragment = new RestaurantsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });

        viewOnMapsButton = (Button) rootView.findViewById(R.id.btViewMaps);
        viewOnMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsFragment fragment = new MapsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });

        aboutButton = (Button) rootView.findViewById(R.id.btAbout);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutFragment fragment = new AboutFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });

        logoutButton = (Button) rootView.findViewById(R.id.btLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);

            }
        });

        return rootView;
    }
}
