package com.anish.recepies.repositories.server;

import com.anish.recepies.models.dto.DTO_Recipe;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {


    @GET
    Observable<DTO_Recipe> getRecipes(@Url String type);

}
