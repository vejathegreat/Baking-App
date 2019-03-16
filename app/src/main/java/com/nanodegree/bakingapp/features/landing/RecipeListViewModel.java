package com.nanodegree.bakingapp.features.landing;


import com.nanodegree.bakingapp.models.Recipe;
import com.nanodegree.bakingapp.repository.RecipeRepository;
import com.nanodegree.bakingapp.repository.RecipeRepositoryImpl;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecipeListViewModel extends ViewModel {

    final MutableLiveData<List<Recipe>> recipeResponse = new MutableLiveData<>();
    final MutableLiveData<Throwable> errorStatus = new MutableLiveData<>();

    void retrieveRecipes(){

        RecipeRepository repository = new RecipeRepositoryImpl();

        repository.getRecipes(new RecipeRepository.RecipeRepositoryCallback() {
            @Override
            public void onSuccess(List<Recipe> recipe) {
                recipeResponse.setValue(recipe);
            }

            @Override
            public void onFailure(Throwable t) {
                errorStatus.setValue(t);
            }
        });
    }
}