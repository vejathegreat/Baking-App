package com.nanodegree.bakingapp.features.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.nanodegree.bakingapp.R;
import com.nanodegree.bakingapp.models.Step;
import com.nanodegree.bakingapp.utils.Constants;

import java.util.List;

import androidx.fragment.app.Fragment;

public class StepDetailFragment extends Fragment implements View.OnClickListener {

    private List<Step> stepList;
    private int currentPosition;
    private int maxPosition;
    private Boolean mTwoPane;

   ExoPlayer video;
    TextView shortDescriptionTextView;
   TextView descriptionTextView;
   Button nextButton;
   Button previousButton;
   LinearLayout buttonContainer;

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
            mTwoPane = (Boolean) bundle.get(Constants.TWO_PANE);
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

        ExoPlayer video = view.findViewById(R.id.video);
        buttonContainer = view.findViewById(R.id.button_container);
        shortDescriptionTextView = view.findViewById(R.id.short_description_textView);
        descriptionTextView = view.findViewById(R.id.description_textview);
        nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
        previousButton = view.findViewById(R.id.previous_button);
        previousButton.setOnClickListener(this);
    }
    private void updateView() {
        Step step = stepList.get(currentPosition);

        if(currentPosition == 0){
            previousButton.setVisibility(View.GONE);
        }else if(currentPosition + 1 == maxPosition){
            nextButton.setVisibility(View.GONE);
        }else {
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }

        shortDescriptionTextView.setText(step.getShortDescription());
        descriptionTextView.setText(step.getDescription());
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
