package com.example.learn_english_smart.SearchImage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Image {
    //API:      https://pixabay.com/api/?key=30364055-ba526c17b5bca96e736c16990&q=cat&image_type=photo&pretty=true

    Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    API_Image API_IMAGE = new Retrofit.Builder().
            baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build()
            .create(API_Image.class);

    @GET("api/")
    Call<GetImage> GET_IMAGE_CALL(@Query("key") String key,
                                  @Query("q") String Search,
                                  @Query("image_type") String photo,
                                  @Query("pretty") Boolean w);
}
