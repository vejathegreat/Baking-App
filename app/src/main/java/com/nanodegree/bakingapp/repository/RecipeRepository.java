package com.nanodegree.bakingapp.repository;


import com.nanodegree.bakingapp.models.Recipe;

import java.util.List;

import androidx.annotation.NonNull;

public interface RecipeRepository {

    interface RecipeRepositoryCallback{
        void onSuccess(List<Recipe> recipe);
        void onFailure(Throwable t);
    }

    void getRecipes(@NonNull RecipeRepository.RecipeRepositoryCallback repositoryCallback);
}