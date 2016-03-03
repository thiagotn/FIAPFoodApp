package cc.thiago.fiapfoodapp.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.app.SimpleRealmApp;
import cc.thiago.fiapfoodapp.model.Restaurant;
import cc.thiago.fiapfoodapp.realm.table.RealmTable;
import cc.thiago.fiapfoodapp.view.activity.MainActivity;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmDeleteFragment extends Fragment {

    View view;
    Button btConfirm;
    Button btCancel;
    String restaurantId;
    private Realm realm;

    public ConfirmDeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_delete, container, false);

        restaurantId = getArguments().getString("restaurantId");
        Log.i("RestaurantId", "restaurantId: " + restaurantId);

        btConfirm = (Button) view.findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRestaurant(restaurantId);
                listRestaurants();
            }
        });

        btCancel = (Button) view.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRestaurant(restaurantId);
            }
        });

        return view;
    }

    private void deleteRestaurant(final String restaurantId) {
        realm = Realm.getInstance(SimpleRealmApp.getInstance());
        realm.beginTransaction();
        Restaurant restaurant = realm.where(Restaurant.class).equalTo(RealmTable.ID, restaurantId).findFirst();
        // TODO: remover foto do sdcard
        restaurant.removeFromRealm();
        realm.commitTransaction();
    }

    private void showRestaurant(final String restaurantId) {
        Bundle bundle = new Bundle();
        bundle.putString("restaurantId", restaurantId);
        ShowRestaurantFragment fragment = new ShowRestaurantFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void listRestaurants() {
        RestaurantsFragment fragment = new RestaurantsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
