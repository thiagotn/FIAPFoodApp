package cc.thiago.fiapfoodapp.app;

import android.app.Application;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.model.Restaurant;
import cc.thiago.fiapfoodapp.realm.module.SimpleRealmModule;
import cc.thiago.fiapfoodapp.view.fragments.RestaurantsFragment;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by thiagotn on 05/02/2016.
 */
public class SimpleRealmApp extends Application {

    private static SimpleRealmApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(instance.getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .modules(new SimpleRealmModule())
                .build();
    }

    public static SimpleRealmApp getInstance() {
        return instance;
    }

    private void loadRestaurantData() {
        Log.i(RestaurantsFragment.class.getSimpleName(), "loadRestaurantData: Started.");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory jsonFactory = new JsonFactory();
        try {
            JsonParser jsonParserBlog =
                    jsonFactory.createParser(getResources().openRawResource(R.raw.restaurant));
            List<Restaurant> entries =
                    objectMapper.readValue(jsonParserBlog, new TypeReference<List<Restaurant>>() {
                    });

            List<Restaurant> data = new ArrayList<>();

            for (Restaurant r : entries) {
                r.setId(UUID.randomUUID().toString());
                Log.i(RestaurantsFragment.class.getSimpleName(), "Restaurant: name: " + r.getName() + " - type: " + r.getType() );
                data.add(r);
            }

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealm(data);
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            Log.i(RestaurantsFragment.class.getSimpleName(), "Could not load blog data.");
            throw new IllegalStateException("Could not load blog data.");
        }
        Log.i(RestaurantsFragment.class.getSimpleName(), "loadRestaurantData: Finished.");
    }

    public void verifyLoadData() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Restaurant> query = realm.where(Restaurant.class);
        RealmResults<Restaurant> results = query.findAll();
        if (results == null || results.size() == 0) {
            //resetRealm();
            loadRestaurantData();
        }
    }
}
