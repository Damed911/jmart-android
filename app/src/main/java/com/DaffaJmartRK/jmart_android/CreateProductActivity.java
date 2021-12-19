package com.DaffaJmartRK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.DaffaJmartRK.jmart_android.model.Account;
import com.DaffaJmartRK.jmart_android.model.Store;
import com.DaffaJmartRK.jmart_android.request.CreateProductRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CreateProductActivity extends AppCompatActivity {
    private Account loggedAccount;
    private EditText namaProd;
    private EditText beratProd;
    private EditText hargaProd;
    private EditText diskonProd;
    private RadioGroup kondisi;
    private Spinner category;
    private Spinner shipmentPlans;
    private Button createBtn;
    private Button cancelBtn;
    private boolean conditionUsed = true;
    private Byte shipment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        RequestQueue queue = Volley.newRequestQueue(this);
        loggedAccount = LoginActivity.getLoggedAccount();

        namaProd = findViewById(R.id.editName);
        beratProd = findViewById(R.id.editWeight);
        hargaProd = findViewById(R.id.editPrice);
        diskonProd = findViewById(R.id.editDiscount);
        kondisi = findViewById(R.id.condition);
        category = findViewById(R.id.spinCategory);
        shipmentPlans = findViewById(R.id.spinShipment);
        createBtn = findViewById(R.id.createButton);
        cancelBtn = findViewById(R.id.cancelButton);

        kondisi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(kondisi.equals("Used")){
                    conditionUsed = true;
                }
                else if(kondisi.equals("New")){
                    conditionUsed = false;
                }
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = String.valueOf(loggedAccount.id);
                String productName = namaProd.getText().toString();
                String productWeight = beratProd.getText().toString();
                String productPrice = hargaProd.getText().toString();
                String productDiscount = diskonProd.getText().toString();
                String productCategory= category.getSelectedItem().toString();
                String productShipment= shipmentPlans.getSelectedItem().toString();

                switch (productShipment){
                    case "INSTANT":
                        productShipment = String.valueOf(0);
                        break;
                    case "SAME DAY":
                        productShipment = String.valueOf(1);
                        break;
                    case "NEXT DAY":
                        productShipment = String.valueOf(2);
                        break;
                    case "REGULER":
                        productShipment = String.valueOf(3);
                        break;
                    case "KARGO":
                        productShipment = String.valueOf(4);
                        break;
                    default:
                        productShipment = String.valueOf(3);
                        break;
                }
                System.out.println(productCategory + "  " + productShipment);
                CreateProductRequest createProductRequest = new CreateProductRequest(accountId, productName, productWeight,
                        String.valueOf(conditionUsed), productPrice, productDiscount, productCategory, productShipment,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toast.makeText(getApplicationContext(), "Create product successful", Toast.LENGTH_LONG).show();
                                    finish();
                                    //If succesful, go back to/and reload the Main Activity
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Create product unsuccessful, error occurred", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Create product unsuccessful, can't connect to server", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(createProductRequest);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}