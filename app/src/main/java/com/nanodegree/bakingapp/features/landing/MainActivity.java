package com.nanodegree.bakingapp.features.landing;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.nanodegree.bakingapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_list_recyclerView)
    RecyclerView recipeRecyclerView;

    private RecipeListViewModel recipesViewModel;
    private RecipesListAdapter recipesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpViewModel();
        recipesViewModel.retrieveRecipes();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        int orientation = this.getResources().getConfiguration().orientation;
        recipesListAdapter = new RecipesListAdapter(this);

        if(!isTablet(this)){
            if(orientation  == Configuration.ORIENTATION_PORTRAIT){
                recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            }else if(orientation  == Configuration.ORIENTATION_LANDSCAPE){
                recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            }
        }
        else{
            recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        recipeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        recipeRecyclerView.setAdapter(recipesListAdapter);
    }

    private void setUpViewModel() {
        recipesViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        observeRecipes();
    }

    private void observeRecipes() {
        recipesViewModel.recipeResponse.observe(this, recipes -> recipesListAdapter.setItem(recipes));
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
