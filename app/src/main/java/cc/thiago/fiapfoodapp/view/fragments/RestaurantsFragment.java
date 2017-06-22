package cc.thiago.fiapfoodapp.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.app.SimpleRealmApp;
import cc.thiago.fiapfoodapp.model.Restaurant;
import cc.thiago.fiapfoodapp.view.search.RestaurantRecyclerViewAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {

    private RealmSearchView realmSearchView;
    private RestaurantRecyclerViewAdapter adapter;
    private Realm realm;

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(RestaurantsFragment.class.getSimpleName(), "onCreateView: Started.");

        SimpleRealmApp.getInstance().verifyLoadData();

        View view = inflater.inflate(R.layout.search, container, false);

        realmSearchView = (RealmSearchView) view.findViewById(R.id.search_view);

        realm = Realm.getDefaultInstance();
        adapter = new RestaurantRecyclerViewAdapter(getContext(), realm, "name");
        realmSearchView.setAdapter(adapter);

        // Inflate the layout for this fragment
        Log.i(RestaurantsFragment.class.getSimpleName(), "onCreateView: Finished.");
        //return inflater.inflate(R.layout.fragment_restaurants, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    private void resetRealm() {
        Log.i(RestaurantsFragment.class.getSimpleName(), "resetRealm: Started.");
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(realmConfig);
        Log.i("LOG", "resetRealm: Finished.");
    }
}
