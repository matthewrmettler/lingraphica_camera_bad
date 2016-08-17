package com.example.android.camera2basic.tests;

import android.app.Activity;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
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
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.example.android.camera2basic.Helper.getPhotoCount;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CameraCaptureTest {

    private static final String TAG = "CameraCaptureTest";
    @Rule
    public ActivityTestRule<CameraActivity> mActivityTestRule = new ActivityTestRule<>(CameraActivity.class);

    @Test
    /**
     * Tests whether pushing the camera capture button increments the number of photos by 1.
     * Realistically, we should be checking if a new file was created that matches the photo just taken;
     * However, I'm not sure how to do that with all the various background threads and camera2 features that
     * are being used to generate the photo, create an ImageSaver object, and have that object be processed.
     */
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

}
