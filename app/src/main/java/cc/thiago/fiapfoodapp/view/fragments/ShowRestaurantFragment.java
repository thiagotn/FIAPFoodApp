package cc.thiago.fiapfoodapp.view.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.app.SimpleRealmApp;
import cc.thiago.fiapfoodapp.model.Restaurant;
import cc.thiago.fiapfoodapp.realm.table.RealmTable;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowRestaurantFragment extends Fragment {

    View view;
    TextView tvRestaurantName;
    TextView tvRestaurantType;
    TextView tvRestaurantDesc;
    TextView tvRestaurantAvgCost;
    TextView tvRestaurantPhoneNumber;
    Button btBackToRestaurants;
    ImageView ivPreview;

    private Realm realm;

    public ShowRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_restaurant, container, false);

        ivPreview = (ImageView) view.findViewById(R.id.ivPreview);

        tvRestaurantName = (TextView) view.findViewById(R.id.et_restaurant_name);
        tvRestaurantType = (TextView) view.findViewById(R.id.et_restaurant_type);
        tvRestaurantDesc = (TextView) view.findViewById(R.id.et_restaurant_desc);
        tvRestaurantAvgCost = (TextView) view.findViewById(R.id.et_restaurant_cost);
        tvRestaurantPhoneNumber = (TextView) view.findViewById(R.id.et_restaurant_phone);

        btBackToRestaurants = (Button) view.findViewById(R.id.btBackToListRestaurant);
        btBackToRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listRestaurants();
            }
        });

        realm = Realm.getInstance(SimpleRealmApp.getInstance());
        String restaurantId = getArguments().getString("restaurantId");
        Log.i("RestaurantId", "restaurantId: " + restaurantId);
        loadRestaurant(restaurantId);

        return view;
    }

    private void listRestaurants() {
        RestaurantsFragment fragment = new RestaurantsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void loadRestaurant(final String restaurantId) {
        Restaurant result = realm.where(Restaurant.class).equalTo(RealmTable.ID, restaurantId).findFirst();
        Log.i("ShowRestaurantFrament", "restaurantId: " + result.getId());
        tvRestaurantName.setText(result.getName());
        tvRestaurantType.setText(result.getType());
        tvRestaurantDesc.setText(result.getDescription());
        tvRestaurantAvgCost.setText("R$" + String.valueOf(result.getAverageCost()));
        tvRestaurantPhoneNumber.setText(result.getPhone());
        setPhoto(result.getPathPhoto());
    }

    private void setPhoto(String photoPath) {
        try {
            ivPreview.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // Redimensionamento da imagem para não lançar exceção OutOfMemory para imagens muito grande
            options.inSampleSize = 2;
            final Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
            ivPreview.setImageBitmap(bitmap);
        } catch(Exception e) {
            Log.i("Foto", "Falha ao carregar imagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
