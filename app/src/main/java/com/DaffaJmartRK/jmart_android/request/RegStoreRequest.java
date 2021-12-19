package com.DaffaJmartRK.jmart_android.request;

import com.DaffaJmartRK.jmart_android.LoginActivity;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegStoreRequest extends StringRequest{
    private final Map<String, String> params;

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
    public Map<String,String> getParams(){return params;}
}
