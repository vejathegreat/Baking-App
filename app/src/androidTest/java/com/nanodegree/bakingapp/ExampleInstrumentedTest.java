package com.nanodegree.bakingapp;


import com.nanodegree.bakingapp.features.landing.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testThatRecipeListAreDisplayedToRecycler() throws InterruptedException {
        Thread.sleep(2000);
        if (getRecipeRecyclerViewItemCount() > 0) {
            onView(withId(R.id.recipe_list_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
    }

    @Test
    public void testThatRecipeDetailsAreDisplayedWhenItemSelected() throws InterruptedException {
        Thread.sleep(2000);
        if (getRecipeRecyclerViewItemCount() > 0) {
            onView(withId(R.id.recipe_list_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
        onView(withId(R.id.activity_step_list_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void testStepListItemsAreDisplayedWhenStepClicked() throws InterruptedException {
        Thread.sleep(2000);
        if (getRecipeRecyclerViewItemCount() > 0) {
            onView(withId(R.id.recipe_list_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
        Thread.sleep(4000);
        onView(withId(R.id.activity_step_list_layout)).check(matches(isDisplayed()));
    }

    private int getRecipeRecyclerViewItemCount() {
        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.recipe_list_recyclerView);
        return recyclerView.getAdapter().getItemCount();
    }

}
