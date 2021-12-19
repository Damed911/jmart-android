package com.DaffaJmartRK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk melakukan request ketika ingin melakukan TopUp saldo
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class TopUpRequest extends StringRequest {
    /**
     * Instance variable TopUpRequest
     */
    private final Map<String,String> params;

    /**
     * Constructor method TopUpRequest
     * @param id
     * @param balance
     * @param listener
     * @param errorListener
     */
    public TopUpRequest(
            int id,
            double balance,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ){
        super(Method.POST, "http://10.0.2.2:3090/account/"+id+"/topUp", listener, errorListener);
        params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("balance", String.valueOf(balance));
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return params
     */
    public Map<String, String> getParams(){return params;}
}
