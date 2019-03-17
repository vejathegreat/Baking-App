package com.nanodegree.bakingapp.features.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.bakingapp.R;
import com.nanodegree.bakingapp.models.Step;
import com.nanodegree.bakingapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private List<Step> stepList;
    private Boolean mTwoPane;
    private StepListActivity stepListActivity;

    StepListAdapter(List<Step> stepList, Boolean mTwoPane, StepListActivity stepListActivity) {
        this.stepList = stepList;
        this.mTwoPane = mTwoPane;
        this.stepListActivity = stepListActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Step step = stepList.get(position);
        viewHolder.stepNumberTextView.setText(String.format(Locale.US, "%x", step.getId()));
        viewHolder.shortDescriptionTextView.setText(step.getShortDescription());
        viewHolder.itemView.setOnClickListener(v -> {
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putParcelableArrayList(Constants.STEPS_KEY, (ArrayList<? extends Parcelable>) stepList);
                arguments.putInt(Constants.LIST_POSITION, position);
                arguments.putBoolean(Constants.TWO_PANE, mTwoPane);
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);
                stepListActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            } else {
                Context context = viewHolder.itemView.getContext();
                Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putExtra(Constants.LIST_POSITION, position);
                intent.putParcelableArrayListExtra(Constants.STEPS_KEY, (ArrayList<? extends Parcelable>) stepList);
                intent.putExtra(Constants.TWO_PANE, mTwoPane);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepNumberTextView;
        TextView shortDescriptionTextView;

        ViewHolder(View view) {
            super(view);
            stepNumberTextView = view.findViewById(R.id.step_number);
            shortDescriptionTextView = view.findViewById(R.id.step_short_description_textView);
        }
    }
}
