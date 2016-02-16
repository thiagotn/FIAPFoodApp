package cc.thiago.fiapfoodapp.realm.repository;

import java.util.List;

import cc.thiago.fiapfoodapp.model.Restaurant;
import io.realm.RealmResults;

/**
 * Created by thiagotn on 05/02/2016.
 */
public interface IRestaurantRepository {

    void addRestaurant(Restaurant restaurant);

    void deleteRestaurantById(String Id);

    void deleteRestaurantByPosition(int position);

    RealmResults<Restaurant>getAllRestaurants();

    Restaurant getRestaurantById(String id);
}
