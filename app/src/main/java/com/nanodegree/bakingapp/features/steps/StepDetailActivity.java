package com.nanodegree.bakingapp.features.steps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.nanodegree.bakingapp.R;
import com.nanodegree.bakingapp.utils.Constants;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(Constants.STEPS_KEY,
                    getIntent().getParcelableArrayListExtra(Constants.STEPS_KEY));
            arguments.putInt(Constants.LIST_POSITION, (Integer) getIntent().getExtras().get(Constants.LIST_POSITION));
            arguments.putBoolean(Constants.TWO_PANE, (Boolean) getIntent().getExtras().get(Constants.TWO_PANE));
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, StepListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
