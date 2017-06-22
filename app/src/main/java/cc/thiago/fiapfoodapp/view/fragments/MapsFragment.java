package cc.thiago.fiapfoodapp.view.fragments;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.app.SimpleRealmApp;
import cc.thiago.fiapfoodapp.model.Restaurant;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_maps, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        realm = Realm.getDefaultInstance();
        RealmQuery<Restaurant> query = realm.where(Restaurant.class);
        RealmResults<Restaurant> results = query.findAll();
        Iterator iterator = results.iterator();
        while(iterator.hasNext()){
            Restaurant restaurant = (Restaurant) iterator.next();
            String localization = restaurant.getLocalization();
            if (TextUtils.isEmpty(localization)) {
                continue;
            }
            String[] parts = localization.split(",");
            Double latitude = Double.parseDouble(parts[0]);
            Double longitude = Double.parseDouble(parts[1]);

            Log.i("Maps", "Adicionando restaurante " + restaurant.getName()
                    + " no Mapa. Lat: " + latitude
                    + " - Long: " + longitude);

            // create marker
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(restaurant.getName());

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            // adding marker
            googleMap.addMarker(marker);

            /*
            */
        }

        // FIXME: CORRIGIR ISTO... NULLPOINTER
        //googleMap.setMyLocationEnabled(true);
        //Location location = googleMap.getMyLocation();
        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // TODO: Temporario, ate a correcao acima
        LatLng latLng = new LatLng(-23.573270, -46.623094);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}