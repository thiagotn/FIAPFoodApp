package cc.thiago.fiapfoodapp.view.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.model.Restaurant;
import cc.thiago.fiapfoodapp.view.activity.MainActivity;
import cc.thiago.fiapfoodapp.view.fragments.ShowRestaurantFragment;
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import io.realm.Realm;

/**
 * Created by thiagotn on 15/02/16.
 */
public class RestaurantRecyclerViewAdapter extends RealmSearchAdapter<Restaurant, RestaurantRecyclerViewAdapter.ViewHolder> {

    Context context;

    public RestaurantRecyclerViewAdapter(
            Context context,
            Realm realm,
            String filterColumnName) {
        super(context, realm, filterColumnName);
        this.context = context;
    }

    public class ViewHolder extends RealmSearchViewHolder {

        private RestaurantItemView restaurantItemView;

        public ViewHolder(FrameLayout container, TextView footerTextView) {
            super(container, footerTextView);
        }

        public ViewHolder(RestaurantItemView restaurantItemView) {
            super(restaurantItemView);
            this.restaurantItemView = restaurantItemView;
        }
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        ViewHolder vh = new ViewHolder(new RestaurantItemView(viewGroup.getContext()));
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(final ViewHolder viewHolder, final int position) {
        final Restaurant restaurant = realmResults.get(position);
        viewHolder.restaurantItemView.bind(restaurant);
        viewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("INFO", "Restaurante clicado: " + position + " - id: " + restaurant.getId());
                        showRestaurant(restaurant.getId());
                    }
                }
        );
    }

    @Override
    public ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.footer_view, viewGroup, false);
        return new ViewHolder(
                (FrameLayout) v,
                (TextView) v.findViewById(R.id.footer_text_view));
    }

    @Override
    public void onBindFooterViewHolder(ViewHolder holder, final int position) {
        super.onBindFooterViewHolder(holder, position);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("INFO", "onBindFooterViewHolder position: " + position);
                    }
                }
        );
    }

    private void showRestaurant(final String restaurantId) {
        Bundle bundle = new Bundle();
        bundle.putString("restaurantId", restaurantId);
        ShowRestaurantFragment fragment = new ShowRestaurantFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
