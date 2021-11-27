package com.DaffaJmartRK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DaffaJmartRK.jmart_android.request.RegisterRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etEmail = findViewById(R.id.emailRegister);
        EditText etNama = findViewById(R.id.nameRegister);
        Button btnRegister = findViewById(R.id.registerButton);
        EditText txtPassword = findViewById(R.id.passwordText);

        String name = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String password = txtPassword.getText().toString();

        btnRegister.setOnClickListener(v ->{
            RegisterRequest request = new RegisterRequest(name, email, password, this, this);
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show();
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