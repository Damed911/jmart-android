package com.DaffaJmartRK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.DaffaJmartRK.jmart_android.model.Account;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvUser = findViewById(R.id.textMain);
        Account loggedAccount = LoginActivity.getLoggedAccount();
        tvUser.setText(loggedAccount.name);
    }
}