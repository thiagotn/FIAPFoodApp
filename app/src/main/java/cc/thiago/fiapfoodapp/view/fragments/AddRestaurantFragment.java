package cc.thiago.fiapfoodapp.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.app.SimpleRealmApp;
import cc.thiago.fiapfoodapp.model.Restaurant;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRestaurantFragment extends Fragment implements LocationListener {

    public static final String PHOTO_ID = "PHOTO_ID";

    // Activity Request Codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // Nome do diretório que serão gravadas as imagens  e os vídeos
    private static final String IMAGE_DIRECTORY_NAME = "fiapfoodapp";

    // URL de armazenamento do arquivo(imagem/video)
    private Uri fileUri;

    View view;
    Button btAddPhoto;
    EditText etName;
    EditText etType;
    EditText etDescription;
    EditText etAverageCost;
    EditText etPhone;
    Button btAddRestaurant;
    ImageView ivPreview;

    private Restaurant restaurant;
    private Realm realm;

    private Double latitude;
    private Double longitude;

    public AddRestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_restaurant, container, false);

        btAddPhoto = (Button) view.findViewById(R.id.bt_add_photo);
        btAddPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

        etName = (EditText) view.findViewById(R.id.et_restaurant_name);
        etType = (EditText) view.findViewById(R.id.et_restaurant_type);
        etDescription = (EditText) view.findViewById(R.id.et_restaurant_desc);
        etAverageCost = (EditText) view.findViewById(R.id.et_restaurant_cost);
        etPhone = (EditText) view.findViewById(R.id.et_restaurant_phone);

        ivPreview = (ImageView) view.findViewById(R.id.ivPreview);

        restaurant = new Restaurant();

        btAddRestaurant = (Button) view.findViewById(R.id.bt_add_restaurant);
        btAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidRestaurant()) {
                    // Realm insert
                    //restaurantRepository.addRestaurant(restaurant);
                    realm = Realm.getInstance(SimpleRealmApp.getInstance());
                    realm.beginTransaction();
                    Restaurant r = realm.createObject(Restaurant.class);
                    r.setId(UUID.randomUUID().toString());
                    r.setName(restaurant.getName());
                    r.setPhone(restaurant.getPhone());
                    r.setType(restaurant.getType());
                    r.setAverageCost(restaurant.getAverageCost());
                    r.setDescription(restaurant.getDescription());
                    r.setPathPhoto(restaurant.getPathPhoto());
                    Log.i("Foto", "Set PathPhoto: " + r.getPathPhoto());
                    //r.setLocalization(restaurant.getLocalization());
                    realm.commitTransaction();

                    listRestaurants();

                } else {
                    //Toast.makeText(view.getContext(), R.string.invalidRestaurant, Toast.LENGTH_LONG).show();
                    Log.i("LOG", "Dados invalidos");
                }
            }
        });

        Bundle extra = this.getArguments();
        if (extra != null && !TextUtils.isEmpty(extra.getString(PHOTO_ID))) {
            String photoPath = extra.getString(PHOTO_ID);
            restaurant.setPathPhoto(photoPath);
            btAddPhoto.setVisibility(View.GONE);
            setPhoto(photoPath);
        }

        return view;
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
            Log.i("Foto", "Falha ao carregar imagem");
        }
    }

    private boolean isValidRestaurant() {
        String name = etName.getText().toString();
        Log.i("LOG", "Name: " + name);
        if (TextUtils.isEmpty(name)) {
            Log.i("LOG", "Nome invalido: " + name);
            return false;
        }

        String type = etType.getText().toString();
        Log.i("LOG", "Type: " + type);
        if (TextUtils.isEmpty(type)) {
            Log.i("LOG", "Type invalido: " + type);
            return false;
        }

        String description = etDescription.getText().toString();
        Log.i("LOG", "Description: " + description);
        if (TextUtils.isEmpty(type)) {
            Log.i("LOG", "Type invalido: " + type);
            return false;
        }

        String averageCost = etAverageCost.getText().toString();
        Log.i("LOG", "AverageCost: " + averageCost);
        if (TextUtils.isEmpty(averageCost)) {
            Log.i("LOG", "AverageCost invalido: " + averageCost);
            return false;
        }

        String phone = etPhone.getText().toString();
        Log.i("LOG", "Phone: " + phone);
        if (TextUtils.isEmpty(phone)) {
            Log.i("LOG", "Phone invalido: " + phone);
            return false;
        }


        restaurant.setName(name);
        restaurant.setType(type);
        restaurant.setDescription(description);
        restaurant.setAverageCost(Double.parseDouble(averageCost));
        restaurant.setPhone(phone);

        return true;
    }

    private void listRestaurants() {
        RestaurantsFragment fragment = new RestaurantsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    //retorna a imagem ou o video
    private static File getOutputMediaFile(int type) {
        //Caminho onde será gravado o arquivo
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Cria o diretório caso não exista
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, R.string.action_cam_save_img_failed
                        + IMAGE_DIRECTORY_NAME);
                return null;
            }
        }
        // Cria o arquivo
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
            Log.i("FOTO", "MediaFile: " + mediaFile);
        } else {
            return null;
        }
        return mediaFile;
    }

    //Cria o arquivo (imagem/video)
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    //Abrir a camera para tirar a foto
    private void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // Inicia a Intent para tirar a foto
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Here we store the file url as it will be null after returning from camera app
     */
    //
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // salva a url do arquivo caso seja alterada a orientacao da tela
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Verifica se o resultado é referente a a chamada para tirar foto
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(view.getContext(),
                        R.string.action_cam_cancelled, Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(view.getContext(),
                        R.string.action_cam_failed, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    //retorna a imagem capturada para o fragment de adicao de restaurante
    private void previewCapturedImage() {
        try {
            ivPreview.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // Redimensionamento da imagem para não lançar exceção OutOfMemory para imagens muito grande
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            ivPreview.setImageBitmap(bitmap);
            restaurant.setPathPhoto(fileUri.getPath());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.i("Localizacao", "Latitude: " + latitude + " - Longitude: " + longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
