package cc.thiago.fiapfoodapp.realm.repository;

import cc.thiago.fiapfoodapp.model.Restaurant;
import io.realm.RealmResults;

/**
 * Created by thiagotn on 05/02/2016.
 */
public interface IRestaurantRepository {

    interface OnAddRestaurantCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetAllRestaurantCallback {
        void onSuccess(RealmResults<Restaurant> universities);
        void onError(String message);
    }

    interface OnGetRestaurantByIdCallback {
        void onSuccess(Restaurant university);
        void onError(String message);
    }

    interface OnDeleteRestaurantCallback {
        void onSuccess();
        void onError(String message);
    }

    void addRestaurant(Restaurant restaurant, OnAddRestaurantCallback callback);

    void deleteRestaurantById(String Id, OnDeleteRestaurantCallback callback);

    void deleteRestaurantByPosition(int position, OnDeleteRestaurantCallback callback);

    void getAllRestaurants(OnGetAllRestaurantCallback callback);

    void getRestaurantById(String id, OnGetRestaurantByIdCallback callback);
}
