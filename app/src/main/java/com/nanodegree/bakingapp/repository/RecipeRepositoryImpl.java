package com.nanodegree.bakingapp.repository;

import com.nanodegree.bakingapp.models.Recipe;
import com.nanodegree.bakingapp.remote.AppClient;
import com.nanodegree.bakingapp.remote.RequestInterface;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepositoryImpl implements RecipeRepository {

    @Override
    public void getRecipes(@NonNull final RecipeRepositoryCallback repositoryCallback) {
        RequestInterface requestInterface = AppClient.getApi();

        requestInterface.getAllRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                repositoryCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                repositoryCallback.onFailure(t);
            }
        });
    }
}