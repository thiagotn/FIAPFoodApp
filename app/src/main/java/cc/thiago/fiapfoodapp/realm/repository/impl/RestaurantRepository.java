package cc.thiago.fiapfoodapp.realm.repository.impl;

import java.util.UUID;

import cc.thiago.fiapfoodapp.app.SimpleRealmApp;
import cc.thiago.fiapfoodapp.model.Restaurant;
import cc.thiago.fiapfoodapp.realm.repository.IRestaurantRepository;
import cc.thiago.fiapfoodapp.realm.table.RealmTable;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by thiagotn on 05/02/2016.
 */
public class RestaurantRepository implements IRestaurantRepository {

    @Override
    public void addRestaurant(Restaurant restaurant, OnAddRestaurantCallback callback) {
        Realm realm = Realm.getInstance(SimpleRealmApp.getInstance());
        realm.beginTransaction();
        Restaurant r = realm.createObject(Restaurant.class);
        r.setId(UUID.randomUUID().toString());
        r.setName(restaurant.getName());
        r.setPhone(restaurant.getPhone());
        r.setType(restaurant.getType());
        r.setAverageCost(restaurant.getAverageCost());
        r.setDescription(restaurant.getDescription());
        r.setLocalization(restaurant.getLocalization());
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteRestaurantById(String Id, OnDeleteRestaurantCallback callback) {
        Realm realm = Realm.getInstance(SimpleRealmApp.getInstance());
        realm.beginTransaction();
        Restaurant restaurant = realm.where(Restaurant.class).equalTo(RealmTable.ID, Id).findFirst();
        restaurant.removeFromRealm();
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteRestaurantByPosition(int position, OnDeleteRestaurantCallback callback) {
        Realm realm = Realm.getInstance(SimpleRealmApp.getInstance());
        realm.beginTransaction();
        RealmQuery<Restaurant> query = realm.where(Restaurant.class);
        RealmResults<Restaurant> results = query.findAll();
        results.remove(position);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getAllRestaurants(OnGetAllRestaurantCallback callback) {
        Realm realm = Realm.getInstance(SimpleRealmApp.getInstance());
        RealmQuery<Restaurant> query = realm.where(Restaurant.class);
        RealmResults<Restaurant> results = query.findAll();

        if (callback != null)
            callback.onSuccess(results);
    }

    @Override
    public void getRestaurantById(String id, OnGetRestaurantByIdCallback callback) {
        Realm realm = Realm.getInstance(SimpleRealmApp.getInstance());
        Restaurant result = realm.where(Restaurant.class).equalTo(RealmTable.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(result);
    }
}
