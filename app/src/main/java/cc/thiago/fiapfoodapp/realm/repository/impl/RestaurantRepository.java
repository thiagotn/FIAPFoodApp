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
    public void addRestaurant(Restaurant restaurant) {
        Realm realm = Realm.getDefaultInstance();
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
    }

    @Override
    public void deleteRestaurantById(String Id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Restaurant restaurant = realm.where(Restaurant.class).equalTo(RealmTable.ID, Id).findFirst();
        restaurant.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void deleteRestaurantByPosition(int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<Restaurant> query = realm.where(Restaurant.class);
        RealmResults<Restaurant> results = query.findAll();
        results.remove(position);
        realm.commitTransaction();
    }

    @Override
    public RealmResults<Restaurant> getAllRestaurants() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Restaurant> query = realm.where(Restaurant.class);
        RealmResults<Restaurant> results = query.findAll();
        return results;
    }

    @Override
    public Restaurant getRestaurantById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Restaurant result = realm.where(Restaurant.class).equalTo(RealmTable.ID, id).findFirst();
        return result;
    }
}
