package com.example.android.camera2basic.tests;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.example.android.camera2basic.Camera2BasicFragment;
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
public class FragmentTests extends ActivityInstrumentationTestCase2<CameraActivity> {

    @Rule
    public ActivityTestRule<CameraActivity> mActivityTestRule = new ActivityTestRule<>(CameraActivity.class);

    private static final String TAG = "FragmentTests";
    private CameraActivity mTestActivity;
    private Camera2BasicFragment mTestFragment;

    public FragmentTests() {
        super(CameraActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mTestActivity = getActivity();
        mTestFragment = getActivity().getFragment();
    }

    /**
     * Test if the test fixture has been set up correctly.
     */
    public void testPreconditions() {
        assertNotNull("mTestActivity is null", mTestActivity);
        assertNotNull("mTestFragment is null", mTestFragment);
    }

    /**
     * Tests whether pushing the camera capture button increments the number of photos by 1.
     *
     * Realistically, we should be checking if a new file was created that matches the photo just taken;
     * However, I'm not sure how to do that with all the various background threads and camera2 features that
     * are being used to generate the photo, create an ImageSaver object, and have that object be processed.
     *
     * Also, i'm waiting for 3 seconds to give time for the file to be written. I don't think thread.sleep
     * is the correct solution, but I think it gets the job done.
     */
    @Test
    public void cameraCaptureTest() throws InterruptedException {
        int prevCount = getPhotoCount(mTestActivity);

        ViewInteraction iconButton = onView(
                allOf(withId(R.id.picture), withText("\uE03B"),
                        withParent(withId(R.id.control)),
                        isDisplayed()));
        iconButton.perform(click());
        //Wait for the image to finish processing and for the file to be written to.
        Thread.sleep(3000);
        int postCount = getPhotoCount(mTestActivity);
        Log.i(TAG, "PrevCount = " + prevCount + "; postCount = " + postCount);
        assertTrue("Error, number of photos did not increase by 1", postCount-1 == prevCount);
    }


    /**
     * Test to make sure that the flash button actually toggles the current flash state.
     */
    @Test
    public void flashButtonTest() {

        boolean preFlashState = mTestFragment.getFlashState();

        ViewInteraction iconTextView = onView(
                allOf(withId(R.id.flash_button), isDisplayed()));
        iconTextView.perform(click());

        boolean postFlashState = mTestFragment.getFlashState();

        assertTrue("Flash states are equal", preFlashState != postFlashState);
    }


    /**
     * Makes sure the app works in both orientations; that is, the app doesn't crash, and that all
     * three buttons are visible.
     */
    @Test
    public void orientationTest() {
        //Make sure the app doesn't crash when we go to Landscape.
        mTestActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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

        mTestActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
     *
     * Also, i'm waiting for 2 seconds to give time for the window to open up. I don't think thread.sleep
     * is the correct solution, but I think it gets the job done.
     */
    @Test
    public void testInfoButton() throws InterruptedException {
        ViewInteraction imageButton = onView(
                allOf(withId(R.id.info), withContentDescription("Info"),
                        withParent(withId(R.id.control)),
                        isDisplayed()));
        imageButton.perform(click());
        //Wait for the Dialog box to appear.
        Thread.sleep(2000);
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.message),
                        withText("This sample demonstrates the basic use of Camera2 API. Check the source code to see how you can display camera preview and take pictures."),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }
}
