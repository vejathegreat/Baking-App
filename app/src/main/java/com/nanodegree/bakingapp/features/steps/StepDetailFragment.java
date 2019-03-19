package com.nanodegree.bakingapp.features.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanodegree.bakingapp.R;
import com.nanodegree.bakingapp.models.Step;
import com.nanodegree.bakingapp.utils.Constants;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class StepDetailFragment extends Fragment implements View.OnClickListener {

    private List<Step> stepList;
    private Step step;
    private int currentPosition;
    private int maxPosition;

    private TextView shortDescriptionTextView;
    private TextView descriptionTextView;
    private Button nextButton;
    private Button previousButton;
    private LinearLayout buttonContainer;

    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        Bundle bundle = getArguments();

        setView(rootView);

        if(bundle != null){
            currentPosition = (int) bundle.get(Constants.LIST_POSITION);
            stepList = bundle.getParcelableArrayList(Constants.STEPS_KEY);
            Boolean mTwoPane = (Boolean) bundle.get(Constants.TWO_PANE);
            maxPosition = stepList.size();

            if(mTwoPane)
            {
                buttonContainer.setVisibility(View.GONE);
            }

            updateView();
        }
        return rootView;
    }

    private void setView(View view) {

        buttonContainer = view.findViewById(R.id.button_container);
        shortDescriptionTextView = view.findViewById(R.id.short_description_textView);
        descriptionTextView = view.findViewById(R.id.description_textview);
        nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
        previousButton = view.findViewById(R.id.previous_button);
        previousButton.setOnClickListener(this);
    }

    private void updateView() {
        step = stepList.get(currentPosition);

        if (currentPosition == 0) {
            previousButton.setVisibility(View.GONE);
        } else if (currentPosition + 1 == maxPosition) {
            nextButton.setVisibility(View.GONE);
        } else {
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }

        shortDescriptionTextView.setText(step.getShortDescription());
        descriptionTextView.setText(step.getDescription());

        openVideoFragment();

    }

    private void openVideoFragment()
    {
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.STEP_SINGLE, step);
        videoFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.media_container, videoFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_button:
                currentPosition++;
                updateView();
                break;

            case R.id.previous_button:
                currentPosition--;
                updateView();
                break;
            default:
        }
    }
}
