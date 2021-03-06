package com.example.myrealestateagency.retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Get money conversion rates according to euro base
public interface RetrofitInterface {
    @GET ("latest?base=EUR")
    Call<JsonObject> getPriceConversion();
}
