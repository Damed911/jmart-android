package com.DaffaJmartRK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.DaffaJmartRK.jmart_android.model.Account;
import com.DaffaJmartRK.jmart_android.request.LoginRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
implements Response.Listener<String>, Response.ErrorListener
{
    private static final Gson gson = new Gson();
    private static Account loggedAccount;

    public static Account getLoggedAccount() {
        return loggedAccount;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etEmail = findViewById(R.id.emailLogin);
        EditText etPass = findViewById(R.id.passwordLogin);
        Button btnLogin = findViewById(R.id.loginButton);
        TextView txtRegister = findViewById(R.id.registerText);

        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();

        btnLogin.setOnClickListener(v -> {
            LoginRequest request = new LoginRequest(email, pass, this, this);
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        });
        txtRegister.setOnClickListener(v1 -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        Intent i = new Intent(this, MainActivity.class);
        try{
            JSONObject obj = new JSONObject(response);
            i.putExtra("id", obj.getInt("id"));
        }catch(Exception e){
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
        startActivity(i);
    }
}
