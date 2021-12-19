package com.DaffaJmartRK.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Class untuk melakukan request ketika ingin register akun baru
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class RegisterRequest extends StringRequest {
    /**
     * Instance Variable RegisterRequest
     */
    private static final String URL = "http://10.0.2.2:3090/account/register";
    private final Map<String,String> params;

    /**
     * Constructor Method RegisterRequest
     * @param name          nama user
     * @param email         email user
     * @param password      password user
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public RegisterRequest(String name, String email, String password,
                           Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
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
