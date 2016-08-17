package com.example.android.camera2basic.tests;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.example.android.camera2basic.CameraActivity;
import com.example.android.camera2basic.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.example.android.camera2basic.Helper.getPhotoCount;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FragmentTest {

    private static final String TAG = "FragmentTest";
    @Rule
    public ActivityTestRule<CameraActivity> mActivityTestRule = new ActivityTestRule<>(CameraActivity.class);


    /**
     * Tests whether pushing the camera capture button increments the number of photos by 1.
     *
     * Realistically, we should be checking if a new file was created that matches the photo just taken;
     * However, I'm not sure how to do that with all the various background threads and camera2 features that
     * are being used to generate the photo, create an ImageSaver object, and have that object be processed.
     */
    @Test
    public void cameraCaptureTest() {
        Activity a = mActivityTestRule.getActivity();
        int prevCount = getPhotoCount(a);

        ViewInteraction iconButton = onView(
                allOf(withId(R.id.picture), withText("\uE03B"),
                        withParent(withId(R.id.control)),
                        isDisplayed()));
        iconButton.perform(click());

        int postCount = getPhotoCount(a);
        Log.i(TAG, "PrevCount = " + prevCount + "; postCount = " + postCount);
        assertTrue("Error, number of photos did not increase by 1", postCount-1 == prevCount);
    }


    /**
     * Test to make sure that the flash button actually toggles the current flash state.
     */
    @Test
    public void flashButtonTest() {
        boolean preFlashState = mActivityTestRule.getActivity().getFragment().getFlashState();

        ViewInteraction iconTextView = onView(
                allOf(withId(R.id.flash_button), withText("\uE076"), isDisplayed()));
        iconTextView.perform(click());
        boolean postFlashState = mActivityTestRule.getActivity().getFragment().getFlashState();

        assertTrue("Flash states are equal", preFlashState != postFlashState);
    }


    /**
     * Makes sure the app works in both orientations; that is, the app doesn't crash, and that all
     * three buttons are visible.
     */
    @Test
    public void orientationTest() {
        Activity a = mActivityTestRule.getActivity();
        //Make sure the app doesn't crash when we go to Landscape.
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            //Check for flash button
            ViewInteraction landFlash = onView(
                    allOf(withId(R.id.flash_button), withText("\uE076"),withId(R.id.containers), isDisplayed()));
            landFlash.check(matches(isDisplayed()));
            //Check for camera button
            ViewInteraction landCapture = onView(
                    allOf(withId(R.id.picture), withText("\uE076"),withId(R.id.containers), isDisplayed()));
            landCapture.check(matches(isDisplayed()));
            //Check for info button
            ViewInteraction landInfo = onView(
                    allOf(withId(R.id.flash_button), withText("\uE076"),withId(R.id.containers), isDisplayed()));
            landInfo.check(matches(isDisplayed()));

        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //Check for flash button
            ViewInteraction portFlash = onView(
                    allOf(withId(R.id.flash_button), withText("\uE076"),withId(R.id.containers), isDisplayed()));
            portFlash.check(matches(isDisplayed()));
            //Check for camera button
            ViewInteraction portCapture = onView(
                    allOf(withId(R.id.picture), withText("\uE076"),withId(R.id.containers), isDisplayed()));
            portCapture.check(matches(isDisplayed()));
            //Check for info button
            ViewInteraction portInfo = onView(
                    allOf(withId(R.id.flash_button), withText("\uE076"),withId(R.id.containers), isDisplayed()));
            portInfo.check(matches(isDisplayed()));
    }

    /**
     * Test to make sure the info button works.
     */
    @Test
    public void testInfoButton() {
        ViewInteraction imageButton = onView(
                allOf(withId(R.id.info), withContentDescription("Info"),
                        withParent(withId(R.id.control)),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.message),
                        withText("This sample demonstrates the basic use of Camera2 API. Check the source code to see how you can display camera preview and take pictures."),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

    }
}
