package cc.thiago.fiapfoodapp.view.search;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.model.Restaurant;

/**
 * Created by thiagotn on 15/02/16.
 */
public class RestaurantItemView extends RelativeLayout {

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.type)
    TextView type;

    @Bind(R.id.description)
    TextView description;

    public RestaurantItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.restaurant_item_view, this);
        ButterKnife.bind(this);
    }

    public void bind(Restaurant restaurant) {
        name.setText(restaurant.getName());
        type.setText(restaurant.getType());
        description.setText(restaurant.getDescription());
    }
}
