package com.DaffaJmartRK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk melakukan request ketika ingin melakukan login
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class LoginRequest extends StringRequest {
    /**
     * Instance Variable class LoginRequest
     */
    private static final String URL = "http://10.0.2.2:3090/account/login";
    private final Map<String,String> params;

    /**
     * Constructor method LoginRequest
     * @param email         email user
     * @param password      password user
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public LoginRequest(
            String email,
            String password,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    )
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * Mutator method untuk mengambil parameter
     * @return params
     */
    public Map<String,String> getParams(){
        return params;
    }
}
