package cc.thiago.fiapfoodapp.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.app.SimpleRealmApp;
import cc.thiago.fiapfoodapp.model.Restaurant;
import cc.thiago.fiapfoodapp.realm.repository.IRestaurantRepository;
import cc.thiago.fiapfoodapp.realm.repository.impl.RestaurantRepository;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRestaurantFragment extends Fragment {

    View view;
    EditText etName;
    EditText etType;
    EditText etDescription;
    EditText etAverageCost;
    EditText etPhone;
    Button btAddRestaurant;

    private Restaurant restaurant;
    private Realm realm;

    public AddRestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_restaurant, container, false);

        etName = (EditText) view.findViewById(R.id.et_restaurant_name);
        etType = (EditText) view.findViewById(R.id.et_restaurant_type);
        etDescription = (EditText) view.findViewById(R.id.et_restaurant_desc);
        etAverageCost = (EditText) view.findViewById(R.id.et_restaurant_cost);
        etPhone = (EditText) view.findViewById(R.id.et_restaurant_phone);
        btAddRestaurant = (Button) view.findViewById(R.id.bt_add_restaurant);
        btAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidRestaurant()) {
                    // Realm insert
                    //restaurantRepository.addRestaurant(restaurant);
                    realm = Realm.getInstance(SimpleRealmApp.getInstance());
                    realm.beginTransaction();
                    Restaurant r = realm.createObject(Restaurant.class);
                    r.setId(UUID.randomUUID().toString());
                    r.setName(restaurant.getName());
                    r.setPhone(restaurant.getPhone());
                    r.setType(restaurant.getType());
                    r.setAverageCost(restaurant.getAverageCost());
                    r.setDescription(restaurant.getDescription());
                    //r.setLocalization(restaurant.getLocalization());
                    realm.commitTransaction();

                    listRestaurants();

                } else {
                    //Toast.makeText(view.getContext(), R.string.invalidRestaurant, Toast.LENGTH_LONG).show();
                    Log.i("LOG", "Dados invalidos");
                }
            }
        });

        return view;
    }

    private boolean isValidRestaurant() {
        String name = etName.getText().toString();
        Log.i("LOG", "Name: " + name);
        if (TextUtils.isEmpty(name)) {
            Log.i("LOG", "Nome invalido: " + name);
            return false;
        }

        String type = etType.getText().toString();
        Log.i("LOG", "Type: " + type);
        if (TextUtils.isEmpty(type)) {
            Log.i("LOG", "Type invalido: " + type);
            return false;
        }

        String description = etDescription.getText().toString();
        Log.i("LOG", "Description: " + description);
        if (TextUtils.isEmpty(type)) {
            Log.i("LOG", "Type invalido: " + type);
            return false;
        }

        String averageCost = etAverageCost.getText().toString();
        Log.i("LOG", "AverageCost: " + averageCost);
        if (TextUtils.isEmpty(averageCost)) {
            Log.i("LOG", "AverageCost invalido: " + averageCost);
            return false;
        }

        String phone = etPhone.getText().toString();
        Log.i("LOG", "Phone: " + phone);
        if (TextUtils.isEmpty(phone)) {
            Log.i("LOG", "Phone invalido: " + phone);
            return false;
        }

        restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setType(type);
        restaurant.setDescription(description);
        restaurant.setAverageCost(Double.parseDouble(averageCost));
        restaurant.setPhone(phone);

        return true;
    }

    private void listRestaurants() {
        RestaurantsFragment fragment = new RestaurantsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
