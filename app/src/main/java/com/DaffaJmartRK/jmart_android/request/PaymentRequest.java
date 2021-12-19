package com.DaffaJmartRK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk melakukan request ketika ingin melakukan pembelian produk
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class PaymentRequest extends StringRequest {
    /**
     * Instance Variable class PaymentRequest
     */
    private static final String URL = "http://10.0.2.2:3090/payment/create";
    private final Map<String,String> params;

    /**
     * Constructor method PaymentRequest
     * @param buyerId       id pembeli
     * @param productId     id produk
     * @param productCount  jumlah produk
     * @param shipmentAdd   alamat pengiriman
     * @param shipmentPlans tipe pengiriman
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public PaymentRequest(String buyerId, String productId, String productCount,
                          String shipmentAdd, String shipmentPlans,
                          Response.Listener<String> listener,
                          Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", buyerId);
        params.put("productId", productId);
        params.put("productCount", productCount);
        params.put("shipmentAdd", shipmentAdd);
        params.put("shipmentPlans", (shipmentPlans));
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return params
     */
    public Map<String, String> getParams(){return params;}
}
