package com.DaffaJmartRK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk melakukan request ketika ingin melakukan register store
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class RegStoreRequest extends StringRequest{
    /**
     * Instance Variable RegStoreRequest
     */
    private final Map<String, String> params;

    /**
     * Constructor method RegStoreRequest
     * @param id            id pembuat
     * @param name          nama toko
     * @param address       alamat toko
     * @param phoneNumber   telepon toko
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public RegStoreRequest(
            int id,
            String name,
            String address,
            String phoneNumber,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    )
    {
        super(Method.POST, "http://10.0.2.2:3090/account/"+id+"/registerStore", listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return params
     */
    public Map<String,String> getParams(){return params;}
}
