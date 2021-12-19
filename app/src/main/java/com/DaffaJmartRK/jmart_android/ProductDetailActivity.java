package com.DaffaJmartRK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.DaffaJmartRK.jmart_android.model.Product;
import com.DaffaJmartRK.jmart_android.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Class untuk menampilkan detail produk ketika di klik pada MainActivity
 * @author ASUS
 * @version Final
 */
public class ProductDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PRICE = "com.DaffaJmartRK.jmart_android.EXTRA_PRICE";
    private Product fetchProduct;
    private TextView namaProduk;
    private TextView beratProduk;
    private TextView hargaProduk;
    private TextView diskonProduk;
    private TextView kondisiProduk;
    private TextView kategoriProduk;
    private TextView shipmentProduk;
    private TextView idPenjual;
    private double prodPrice;
    private static final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        int productId = intent.getIntExtra(MainActivity.EXTRA_PRODUCTID, 0);

        namaProduk = findViewById(R.id.namePrd);
        beratProduk = findViewById(R.id.weightPrd);
        hargaProduk = findViewById(R.id.pricePrd);
        diskonProduk = findViewById(R.id.discPrd);
        kondisiProduk = findViewById(R.id.condPrd);
        kategoriProduk = findViewById(R.id.catPrd);
        shipmentProduk = findViewById(R.id.shipPrd);
        idPenjual = findViewById(R.id.sellerPrd);
        Button buyButton = findViewById(R.id.buyBtn);

        StringRequest fetchedProductRequest = RequestFactory.getById("product", productId, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fetchProduct = gson.fromJson(response, Product.class);
                        idPenjual.setText(String.valueOf(fetchProduct.accountId));
                        namaProduk.setText(fetchProduct.name);
                        beratProduk.setText(String.valueOf(fetchProduct.weight));
                        hargaProduk.setText(String.valueOf(Math.round(fetchProduct.price * 100.00)/100.00));
                        prodPrice = Double.parseDouble(hargaProduk.getText().toString());
                        diskonProduk.setText(String.valueOf(Math.round(fetchProduct.discount * 100.00)/100.00));
                        kondisiProduk.setText(fetchProduct.conditionUsed ? "Used" : "New");
                        kategoriProduk.setText(fetchProduct.category.toString());
                        switch (fetchProduct.shipmentPlans){
                            case 0:
                                shipmentProduk.setText("INSTANT");
                                break;
                            case 1:
                                shipmentProduk.setText("SAME_DAY");
                                break;
                            case 2:
                                shipmentProduk.setText("NEXT_DAY");
                                break;
                            case 4:
                                shipmentProduk.setText("KARGO");
                                break;
                            default:
                                shipmentProduk.setText("REGULER");
                                break;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Fetch product unsuccessful, error occurred", Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(fetchedProductRequest);
        buyButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
            i.putExtra(MainActivity.EXTRA_PRODUCTID, productId);
            i.putExtra(EXTRA_PRICE, prodPrice);
            startActivity(i);
        });
    }
}