package cc.thiago.fiapfoodapp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.view.fragments.RestaurantsFragment;

public class SiginActivity extends AppCompatActivity {

    EditText etUser;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btSigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);

        etUser = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        btSigin = (Button) findViewById(R.id.bt_sign);
        btSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SiginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
