package com.DaffaJmartRK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.DaffaJmartRK.jmart_android.model.Product;

/**
 * Class untuk menjadi tampilan utama dari aplikasi
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewMain.ItemClickListener {
    public static final String EXTRA_PRODUCTID = "com.DaffaJmartRK.jmart_android.EXTRA_PRODUCTID";
    private static final Gson gson = new Gson();
    RecyclerViewMain adapter;
    private CardView cv_product;
    private EditText et_page;
    private int page;
    private CardView cv_filter;
    private EditText inputProductName;
    private EditText inputLowestPrice;
    private EditText inputHighestPrice;
    private CheckBox checkNew;
    private CheckBox checkUsed;
    private Spinner spinner_filterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);

        TabLayout mainTabLayout = findViewById(R.id.mainTabLayout);
        cv_product = findViewById(R.id.product);
        cv_filter = findViewById(R.id.cv_filter);
        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        cv_product.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        cv_filter.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        cv_product.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        cv_filter.setVisibility(View.INVISIBLE);
                        break;
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        List<Product> productNames = new ArrayList<>();
        page = 0;
        fetchProduct(productNames, page, queue, true);
        RecyclerView recyclerView = findViewById(R.id.rv_Products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewMain(this, productNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); //Add divider to each row
        Button btnPrev = findViewById(R.id.btnPrev);
        Button btnNext = findViewById(R.id.buttonNext);
        Button btnGo = findViewById(R.id.buttonGo);
        et_page = findViewById(R.id.tv_page);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page > 0){
                    page--;
                    fetchProduct(productNames, page, queue, true);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                fetchProduct(productNames, page, queue, true);

            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = Integer.parseInt(et_page.getText().toString());
                fetchProduct(productNames, page, queue, true);
            }
        });
        //Filter CardView
        inputProductName = findViewById(R.id.et_productName);
        inputLowestPrice = findViewById(R.id.et_lowestPrice);
        inputHighestPrice = findViewById(R.id.et_highestPrice);
        spinner_filterCategory = findViewById(R.id.spinner_filterCategory);
        checkNew = findViewById(R.id.cb_new);
        checkUsed = findViewById(R.id.cb_used);
        String conditionProduct;
        if(checkNew.isChecked()){
            conditionProduct = checkNew.getText().toString();
        }
        if(checkUsed.isChecked()){
            conditionProduct = checkUsed.getText().toString();
        }
        Button btnApply = findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Filter Product", Toast.LENGTH_LONG).show();
                String productName = inputProductName.getText().toString();
                String lowestPrice= inputLowestPrice.getText().toString();
                String highestPrice = inputHighestPrice.getText().toString();
                String category = spinner_filterCategory.getSelectedItem().toString();
                StringRequest filterRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2:3090/product/getFiltered?pageSize=10&search="+productName+"&minPrice="+lowestPrice+"&maxPrice="+highestPrice+"&category="+category, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonReader reader = new JsonReader(new StringReader(response));
                        try {
                            productNames.clear();
                            reader.beginArray();
                            while(reader.hasNext()){
                                productNames.add(gson.fromJson(reader, Product.class));
                            }
                            adapter.refresh(productNames);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Filter product unsuccessful, error occurred", Toast.LENGTH_LONG).show();
                        }
                        cv_product.setVisibility(View.VISIBLE);
                        cv_filter.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Filtering Successful", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "System Failure", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(filterRequest);
            }
        });
        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Testing Clear", Toast.LENGTH_SHORT).show();
                inputProductName.setText("");
                inputLowestPrice.setText("");
                inputHighestPrice.setText("");
                checkNew.setChecked(false);
                checkUsed.setChecked(false);
                spinner_filterCategory.setSelection(0);
            }
        });
    }

    /**
     * Method untuk mengambil data produk di Json
     * @param productNames      nama produk
     * @param page              halaman produk
     * @param queue             memasukan ke dalam RequestQueue
     * @param refreshAdapter    menghubungkan ke adapter main
     */
    public void fetchProduct(List<Product> productNames, int page, RequestQueue queue, boolean refreshAdapter){
        StringRequest fetchProductsRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2:3090/product/page?page="+page+"&pageSize=10", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonReader reader = new JsonReader(new StringReader(response));
                try {
                    productNames.clear();
                    reader.beginArray();
                    while(reader.hasNext()){
                        productNames.add(gson.fromJson(reader, Product.class));
                    }
                    if(refreshAdapter){
                        adapter.refresh(productNames);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Fetch product unsuccessful, error occurred", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "System Failure", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(fetchProductsRequest);
    }
    //RecycleView Item ClickListener
    @Override
    public void onItemClick(View view, int position) {
        int clickedItemId = adapter.getClickedItemId(position);
        Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCTID, clickedItemId);
        startActivity(intent);
    }

    /**
     * Method untuk membuat menu pada layout main dan hide create product ketika blm memiliki store
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if(LoginActivity.getLoggedAccount().store == null){
            menu.getItem(1).setVisible(false);
        }
        return true;
    }

    /**
     * Method untuk mendirect logo pada menu menuju activity lain
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            case R.id.add:
                startActivity(new Intent(this, CreateProductActivity.class));
                return true;
            case R.id.account:
                startActivity(new Intent(this, AboutMeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}