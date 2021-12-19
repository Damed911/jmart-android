package com.DaffaJmartRK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DaffaJmartRK.jmart_android.model.Account;
import com.DaffaJmartRK.jmart_android.model.Store;
import com.DaffaJmartRK.jmart_android.request.RegStoreRequest;
import com.DaffaJmartRK.jmart_android.request.TopUpRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk semua aktivitas pada layout about me
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class AboutMeActivity extends AppCompatActivity {
    /**
     * Instance Variable AboutMeActivity
     */
    Account loggedAccount;
    TextView namaAkun;
    TextView emailAkun;
    TextView saldoAkun;
    Button topUpBtn;
    EditText jumlahTopUp;
    Button registerStoreBtn;
    EditText regNamaToko;
    EditText regAlamatToko;
    EditText regTeleponToko;
    Button registerBtn;
    Button cancelBtn;
    Button invoiceBtn;
    Button invoiceBtn1;
    TextView namaToko;
    TextView alamatToko;
    TextView teleponToko;
    CardView registerStore;
    CardView showStore;
    public static final Gson gson = new Gson();

    /**
     * Method untuk tiap aspek pada layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        RequestQueue queue = Volley.newRequestQueue(this);

        namaAkun = findViewById(R.id.textView6);
        emailAkun = findViewById(R.id.textView7);
        saldoAkun = findViewById(R.id.textView9);
        topUpBtn = findViewById(R.id.button);
        jumlahTopUp = findViewById(R.id.editTextNumber3);
        registerStoreBtn = findViewById(R.id.button2);
        regNamaToko = findViewById(R.id.editTextTextPersonName2);
        regAlamatToko = findViewById(R.id.editTextTextPersonName3);
        regTeleponToko = findViewById(R.id.editTextNumber5);
        registerBtn = findViewById(R.id.button3);
        cancelBtn = findViewById(R.id.button4);
        invoiceBtn = findViewById(R.id.invoiceButton);
        namaToko = findViewById(R.id.textRamdan);
        alamatToko = findViewById(R.id.textAlamat);
        teleponToko = findViewById(R.id.textNomor);
        registerStore = findViewById(R.id.registerStoreCardView);
        showStore = findViewById(R.id.storeCardView);
        invoiceBtn1 = findViewById(R.id.invoiceButton1);

        loggedAccount = LoginActivity.getLoggedAccount();
        namaAkun.setText(loggedAccount.name);
        emailAkun.setText(loggedAccount.email);
        saldoAkun.setText(String.valueOf(loggedAccount.balance));

        if(loggedAccount.store == null){
            registerStore.setVisibility(View.GONE);
            showStore.setVisibility(View.INVISIBLE);
            //Program ketika tombol registerstore ditekan
            registerStoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerStore.setVisibility(View.VISIBLE);
                    registerStoreBtn.setVisibility(View.GONE);
                }
            });
            //Program ketika tombol cancel ditekan
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerStore.setVisibility(View.GONE);
                    registerStoreBtn.setVisibility(View.VISIBLE);
                }
            });
            //Setelah registerstore ditekan, terdapat tombol register untuk mendaftarkan toko
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String namaToko = regNamaToko.getText().toString();
                    String alamatToko = regAlamatToko.getText().toString();
                    String teleponToko = regTeleponToko.getText().toString();
                    System.out.println(loggedAccount.id);

                    RegStoreRequest req = new RegStoreRequest(loggedAccount.id, namaToko, alamatToko, teleponToko,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject obj = new JSONObject(response);
                                        if(obj != null){
                                            Toast.makeText(AboutMeActivity.this, "Register Store Successful", Toast.LENGTH_LONG).show();
                                            loggedAccount.store = gson.fromJson(obj.toString(), Store.class);
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    }
                                    catch(JSONException e){
                                        Toast.makeText(AboutMeActivity.this, "Register Store Failed", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                            }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AboutMeActivity.this, "System Failure", Toast.LENGTH_LONG).show();
                        }
                    });
                    queue.add(req);
                }
            });
        }
        //Ketika sudah terdapat store, layout showstore akan ditampilkan
        else{
            registerStoreBtn.setVisibility(View.GONE);
            invoiceBtn1.setVisibility(View.GONE);
            registerBtn.setVisibility(View.GONE);
            cancelBtn.setVisibility(View.GONE);
            showStore.setVisibility(View.VISIBLE);
            namaToko.setText(loggedAccount.store.name);
            alamatToko.setText(loggedAccount.store.address);
            teleponToko.setText(loggedAccount.store.phoneNumber);
        }
        //Program untuk menentukan cara kerja dari tombol topup sehingga bisa melakukan topup
        topUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topup = jumlahTopUp.getText().toString();
                System.out.println(topup);
                double balance = Double.parseDouble(topup);
                System.out.println(balance);
                int id = loggedAccount.id;
                TopUpRequest req = new TopUpRequest(id, balance,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("true")){
                                    Toast.makeText(AboutMeActivity.this, "Top Up Successful", Toast.LENGTH_LONG).show();
                                    loggedAccount.balance += balance;
                                    finish();
                                    startActivity(getIntent());
                                }
                                else{
                                    Toast.makeText(AboutMeActivity.this, "Top Up Failed", Toast.LENGTH_LONG).show();
                                    return;
                                }

                            }
                        }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AboutMeActivity.this, "System Failure", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(req);
            }
        });
        //Invoice button di card view store ketika ditekan
        invoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InvoiceHistoryActivity.class);
                startActivity(i);
            }
        });
        //Invoice button di card view utama ketika ditekan
        invoiceBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InvoiceHistoryActivity.class);
                startActivity(i);
            }
        });
    }
}