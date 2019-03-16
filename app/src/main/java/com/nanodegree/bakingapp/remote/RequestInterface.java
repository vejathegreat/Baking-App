package com.nanodegree.bakingapp.remote;

import com.nanodegree.bakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface{
        @GET("baking.json")
        Call<List<Recipe>> getAllRecipes();
}
