package com.dev.newsapp.network;

import com.dev.newsapp.models.CommentsResponse;
import com.dev.newsapp.models.NewsResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("newsapp/getall")
    Call<NewsResponse> doGetListResources();

    @GET("newsapp/getbycategoryid/{category_id}")
    Call<NewsResponse> getNewsByCategory(@Path("category_id") String id);

    @GET("newsapp/getnewsbyid/{newsid}")
    Call<NewsResponse> getNewsById(@Path("newsid") String id);

    @GET("newsapp/getcommentsbynewsid/{newsid}")
    Call<CommentsResponse> getComments(@Path("newsid") String id);

    @POST("newsapp/savecomment")
    Call<CommentsResponse> saveComment(@Body JsonObject jsonObject);



}
