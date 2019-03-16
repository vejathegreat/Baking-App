package com.nanodegree.bakingapp.features.landing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nanodegree.bakingapp.R;
import com.nanodegree.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesListViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();
    private Context context;

    public RecipesListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecipesListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_recipe, viewGroup, false);
        return new RecipesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesListViewHolder recipesListViewHolder, int position) {
        final Recipe recipe = recipeList.get(position);
        recipesListViewHolder.recipeNameTextView.setText(recipe.getName());

        RequestOptions options = new RequestOptions()
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(recipe.getImage())
                .apply(options)
                .into(recipesListViewHolder.recipeImageView);

        recipesListViewHolder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Start Master Flow", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public  void setItem(List<Recipe>recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    class RecipesListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_imageview)
        AppCompatImageView recipeImageView;
        @BindView(R.id.recipe_name_textView) TextView recipeNameTextView;

        RecipesListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
