package com.nanodegree.bakingapp.features.steps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.nanodegree.bakingapp.R;
import com.nanodegree.bakingapp.features.landing.MainActivity;
import com.nanodegree.bakingapp.models.Ingredient;
import com.nanodegree.bakingapp.models.Step;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.nanodegree.bakingapp.utils.Constants.INGREDIENTS_KEY;
import static com.nanodegree.bakingapp.utils.Constants.STEPS_KEY;
import static com.nanodegree.bakingapp.utils.Converters.units;

public class StepListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;

    TextView quantityTextView;
    TextView measureTextView;
    TextView ingredientTextView;
    View row;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            ingredientList = intent.getExtras().getParcelableArrayList(INGREDIENTS_KEY);
            stepList = intent.getExtras().getParcelableArrayList(STEPS_KEY);
            setupIngredientsTable();
        }

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupIngredientsTable() {
        TableLayout tableLayout = findViewById(R.id.ingredients_table);
        for (int i = 0; i < ingredientList.size(); i++) {

            if (i == 0) {
                prepareRow();
                quantityTextView.setText(R.string.quantity);
                measureTextView.setText(R.string.measure);
                ingredientTextView.setText(R.string.ingredient);
                ingredientTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tableLayout.addView(row);
            }
            prepareRow();
            quantityTextView.setText(Double.toString(ingredientList.get(i).getQuantity()));
            measureTextView.setText(units(ingredientList.get(i).getQuantity(), ingredientList.get(i).getMeasure()));
            ingredientTextView.setText(ingredientList.get(i).getIngredient());
            tableLayout.addView(row);
        }
    }

    void prepareRow() {
        row = LayoutInflater.from(this).inflate(R.layout.ingredient_row, null, false);
        quantityTextView = row.findViewById(R.id.quantity_textView);
        measureTextView = row.findViewById(R.id.measure_textView);
        ingredientTextView = row.findViewById(R.id.ingredient_textView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        StepListAdapter stepsAdapter = new StepListAdapter(stepList, mTwoPane, this);
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(stepsLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(stepsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}