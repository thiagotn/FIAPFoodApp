package cc.thiago.fiapfoodapp.view.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

    @Bind(R.id.imageView)
    ImageView imageView;

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
        if (!TextUtils.isEmpty(restaurant.getPathPhoto())) {
            Log.i("Foto", "PathPhoto: " + restaurant.getPathPhoto());
            setPhoto(restaurant.getPathPhoto());
        }
    }

    private void setPhoto(String photoPath) {
        try {
            imageView.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // Redimensionamento da imagem para não lançar exceção OutOfMemory para imagens muito grande
            options.inSampleSize = 5;
            final Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
            final Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 72, 72, true);

            imageView.setImageBitmap(scaledBitmap);
        } catch(Exception e) {
            Log.i("LOG", "Falha ao carregar imagem");
        }
    }

}
