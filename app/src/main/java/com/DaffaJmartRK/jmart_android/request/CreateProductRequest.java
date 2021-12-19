package com.DaffaJmartRK.jmart_android.request;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk melakukan request ketika ingin membuat suatu produk
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class CreateProductRequest extends StringRequest {
    /**
     * Instance Variable class CreateProductRequest
     */
    private static final String URL = "http://10.0.2.2:3090/product/create";
    private final Map<String, String> params;

    /**
     * Constructor method untuk CreateProductRequest
     * @param accountId     id penjual
     * @param name          nama produk
     * @param weight        berat produk
     * @param conditionUsed kondisi produk
     * @param price         harga produk
     * @param discount      diskon produk
     * @param category      kategori produk
     * @param shipmentPlans pengiriman produk
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public CreateProductRequest(
            String accountId,
            String name,
            String weight,
            String conditionUsed,
            String price,
            String discount,
            String category,
            String shipmentPlans,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ){
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", accountId);
        params.put("name", name);
        params.put("weight", weight);
        params.put("conditionUsed", conditionUsed);
        params.put("price", price);
        params.put("discount", discount);
        params.put("category", category);
        params.put("shipmentPlans", shipmentPlans);
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return params
     */
    public Map<String,String> getParams(){return params;}
}
