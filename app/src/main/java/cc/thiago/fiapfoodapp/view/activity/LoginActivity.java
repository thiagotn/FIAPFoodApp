package cc.thiago.fiapfoodapp.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cc.thiago.fiapfoodapp.R;
import cc.thiago.fiapfoodapp.view.fragments.RestaurantsFragment;

public class LoginActivity extends AppCompatActivity {

    EditText etUser;
    EditText etPassword;
    Button btLogin;
    Button btSign;
    boolean logged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logged = false;
        etUser = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LOG", "Acao de Login.");
                // validate login and go to fragment main
                if (isValidLogin()) {
                    logged = true;
                    Log.i("LOG", "Login eh valido. Tentando ir para MainActivity");
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Log.i("LOG", "Login eh invalido");
                    Toast.makeText(getApplicationContext(), R.string.msg_invalid_login, Toast.LENGTH_LONG).show();
                }

            }
        });
        btSign = (Button) findViewById(R.id.bt_sign);
        btSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LOG", "Acao de Cadastro.");
                Intent i = new Intent(LoginActivity.this, SiginActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean isValidLogin() {
        String user = etUser.getText().toString();
        String password = etPassword.getText().toString();

        if ("admin".equalsIgnoreCase(user)
                || "admin".equalsIgnoreCase(password)) {

            Log.i("LOG", "Login valid: user: '" + user + "' password: '" + password + "'.");

            return true;
        }

        Log.i("LOG", "Login invalid");
        return false;
    }

    private void checkLogin() {
        if (logged) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkLogin();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }
}
