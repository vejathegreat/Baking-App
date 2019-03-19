package com.nanodegree.bakingapp.features.landing;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.nanodegree.bakingapp.R;
import com.nanodegree.bakingapp.models.Recipe;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_list_recyclerView) RecyclerView recipeRecyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.error_state) LinearLayout errorState;
    @BindView(R.id.retry) Button retry;

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
        observeError();
    }

    private void observeError() {
        recipesViewModel.errorStatus.observe(this, throwable -> {
        errorState.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recipeRecyclerView.setVisibility(View.GONE);
        });
    }

    private void observeRecipes() {
        recipesViewModel.recipeResponse.observe(this, recipeList -> {
            recipesListAdapter.setItem(recipeList);
            errorState.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        });
    }

    @OnClick(R.id.retry)
    void tryAgain(){
        progressBar.setVisibility(View.VISIBLE);
        errorState.setVisibility(View.GONE);
        observeRecipes();
    }


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
