package com.nanodegree.bakingapp.features.steps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.nanodegree.bakingapp.R;

import com.nanodegree.bakingapp.features.steps.dummy.DummyContent;
import com.nanodegree.bakingapp.models.Ingredient;
import com.nanodegree.bakingapp.utils.Constants;

import java.util.List;

import static com.nanodegree.bakingapp.utils.Converters.units;


public class StepListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private List<Ingredient> ingredientList;

    TextView quantityTextView;
    TextView measureTextView;
    TextView ingredientTextView;
    View row;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
        }

        Intent intent = getIntent();
        if (intent.getExtras().getParcelableArrayList(Constants.INGREDIENTS_KEY) != null) {
            ingredientList = intent.getExtras().getParcelableArrayList(Constants.INGREDIENTS_KEY);
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
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final StepListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(StepDetailFragment.ARG_ITEM_ID, item.id);
                    StepDetailFragment fragment = new StepDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepDetailActivity.class);
                    intent.putExtra(StepDetailFragment.ARG_ITEM_ID, item.id);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(StepListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
            }
        }
    }
}
