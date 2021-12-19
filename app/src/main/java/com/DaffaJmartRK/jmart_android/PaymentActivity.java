package com.DaffaJmartRK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DaffaJmartRK.jmart_android.model.Account;
import com.DaffaJmartRK.jmart_android.model.Product;
import com.DaffaJmartRK.jmart_android.request.PaymentRequest;
import com.DaffaJmartRK.jmart_android.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Class untuk mengatur pembelian yang dilakuan oleh user
 * @author ASUS
 * @version Final
 */
public class PaymentActivity extends AppCompatActivity {
    public static final String EXTRA_AMOUNT = "com.DaffaJmartRK.jmart_android.EXTRA_AMOUNT";
    public static final String EXTRA_SHIPMENTADDRESS = "com.DaffaJmartRK.jmart_android.EXTRA_SHIPMENTADDRESS";
    private Product fetchedProduct;
    private static final Gson gson = new Gson();
    private TextView namaProduk, namaSeller, kategori, harga, diskon, tipeShipment, saldo, totalBiaya;
    private Button beli, kembali;
    private EditText jumlahProduk, alamat;
    private double productPrice;
    private byte shipmentPlans;

    private static boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        int productId = intent.getIntExtra(MainActivity.EXTRA_PRODUCTID, 0);
        productPrice = intent.getDoubleExtra(ProductDetailActivity.EXTRA_PRICE, 0);
        Account account = LoginActivity.getLoggedAccount();

        namaProduk = findViewById(R.id.productName);
        namaSeller = findViewById(R.id.tv_seller);
        kategori = findViewById(R.id.tv_category);
        harga = findViewById(R.id.tv_price);
        diskon = findViewById(R.id.tv_discount);
        tipeShipment = findViewById(R.id.tv_shipment);
        saldo = findViewById(R.id.tv_balance);
        totalBiaya = findViewById(R.id.tv_total);
        beli = findViewById(R.id.purchaseBtn);
        kembali = findViewById(R.id.cancelBtn);
        jumlahProduk = findViewById(R.id.etAmount);
        alamat = findViewById(R.id.etAddress);

        jumlahProduk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int newAmount;
                try{
                    newAmount = Integer.parseInt(jumlahProduk.getText().toString());
                }catch(NumberFormatException e){
                    newAmount = 0;
                }
                if (!(newAmount > 0)) {
                    jumlahProduk.setText(String.valueOf(1));
                    totalBiaya.setText(String.valueOf(productPrice * 1));
                }else{
                    totalBiaya.setText(String.valueOf(newAmount * productPrice));
                }
            }
        });
        StringRequest fetchProductDataRequest = RequestFactory.getById("product", productId, new Response.Listener<String>(){
            int amount = Integer.parseInt(jumlahProduk.getText().toString());
            @Override
            public void onResponse(String response) {
                fetchedProduct = gson.fromJson(response, Product.class);
                namaProduk.setText(fetchedProduct.name);
                kategori.setText(fetchedProduct.category.toString());
                double productPrice = Math.round((fetchedProduct.price * 100.00)/100.00);
                double productDiscount = Math.round((fetchedProduct.discount * 100.00)/100.0);
                harga.setText(String.valueOf(productPrice));
                diskon.setText(String.valueOf(productDiscount));
                namaSeller.setText(String.valueOf(fetchedProduct.accountId));
                totalBiaya.setText(String.valueOf(amount * (productPrice - productDiscount)));
                saldo.setText(String.valueOf(account.balance));
                shipmentPlans = fetchedProduct.shipmentPlans;
               switch(shipmentPlans){
                   case 0:
                       tipeShipment.setText("INSTANT");
                   case 1:
                       tipeShipment.setText("SAME DAY");
                   case 2:
                       tipeShipment.setText("NEXT DAY");
                   case 4:
                       tipeShipment.setText("KARGO");
                   default:
                       tipeShipment.setText("REGULER");
               }
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "Fetch unsuccessful, error occurred", Toast.LENGTH_LONG).show();
        });
        queue.add(fetchProductDataRequest);
        beli.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), InvoiceHistoryActivity.class);
            intent1.putExtra(EXTRA_AMOUNT, Integer.parseInt(jumlahProduk.getText().toString()));
            if(isEmpty(alamat)){
                Toast.makeText(getApplicationContext(), "Shipment address can't be empty.", Toast.LENGTH_LONG).show();
            }else{
                intent1.putExtra(EXTRA_SHIPMENTADDRESS, alamat.getText().toString());
                PaymentRequest paymentRequest = new PaymentRequest(String.valueOf(LoginActivity.getLoggedAccount().id), String.valueOf(productId), jumlahProduk.getText().toString(), alamat.getText().toString(), String.valueOf(shipmentPlans),
                        (Response.Listener<String>) response -> {
                            try {
                                Toast.makeText(getApplicationContext(), "Payment has been created", Toast.LENGTH_LONG).show();
                                startActivity(intent1);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Create payment unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        }, error -> Toast.makeText(getApplicationContext(), "Create payment unsuccessful", Toast.LENGTH_LONG).show());
                queue.add(paymentRequest);
            }
        });
        kembali.setOnClickListener(v -> finish());
    }
}