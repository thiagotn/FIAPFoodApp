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

import java.util.UUID;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.app.SimpleRealmApp;
import cc.thiago.fiapfoodapp.model.Restaurant;
import cc.thiago.fiapfoodapp.realm.table.RealmTable;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditRestaurantFragment extends Fragment {


    View view;
    Button btAddPhoto;
    EditText etName;
    EditText etType;
    EditText etDescription;
    EditText etAverageCost;
    EditText etPhone;
    Button btEditRestaurant;

    String restaurantId;
    private Restaurant restaurant;

    private Realm realm;

    public EditRestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_restaurant, container, false);

        etName = (EditText) view.findViewById(R.id.et_restaurant_name);
        etType = (EditText) view.findViewById(R.id.et_restaurant_type);
        etDescription = (EditText) view.findViewById(R.id.et_restaurant_desc);
        etAverageCost = (EditText) view.findViewById(R.id.et_restaurant_cost);
        etPhone = (EditText) view.findViewById(R.id.et_restaurant_phone);

        btEditRestaurant = (Button) view.findViewById(R.id.bt_edit_restaurant);
        btEditRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidRestaurant()) {
                    // Realm insert
                    //restaurantRepository.addRestaurant(restaurant);
                    realm = Realm.getInstance(SimpleRealmApp.getInstance());
                    Restaurant result = realm.where(Restaurant.class).equalTo(RealmTable.ID, restaurantId).findFirst();
                    realm.beginTransaction();

                    result.setId(UUID.randomUUID().toString());
                    result.setName(etName.getText().toString());
                    result.setPhone(etPhone.getText().toString());
                    result.setType(etType.getText().toString());
                    result.setAverageCost(Double.valueOf(etAverageCost.getText().toString()));
                    result.setDescription(etDescription.getText().toString());
                    realm.commitTransaction();

                    listRestaurants();

                } else {
                    //Toast.makeText(view.getContext(), R.string.invalidRestaurant, Toast.LENGTH_LONG).show();
                    Log.i("LOG", "Dados invalidos");
                }
            }
        });

        restaurantId = getArguments().getString("restaurantId");
        Log.i("RestaurantId", "restaurantId: " + restaurantId);

        loadRestaurant(restaurantId);

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

        /*
        restaurant.setName(name);
        restaurant.setType(type);
        restaurant.setDescription(description);
        restaurant.setAverageCost(Double.parseDouble(averageCost));
        restaurant.setPhone(phone);
        */

        return true;
    }

    private void listRestaurants() {
        RestaurantsFragment fragment = new RestaurantsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void loadRestaurant(final String restaurantId) {
        realm = Realm.getInstance(SimpleRealmApp.getInstance());
        realm.beginTransaction();
        Restaurant restaurant2 = realm.where(Restaurant.class).equalTo(RealmTable.ID, restaurantId).findFirst();
        realm.commitTransaction();

        etName.setText(restaurant2.getName());
        etType.setText(restaurant2.getType());
        etDescription.setText(restaurant2.getDescription());
        etAverageCost.setText(String.valueOf(restaurant2.getAverageCost()));
        etPhone.setText(restaurant2.getPhone());
    }
}
