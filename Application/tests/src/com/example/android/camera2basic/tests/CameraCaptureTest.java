package com.example.android.camera2basic.tests;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

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

    @Rule
    public ActivityTestRule<CameraActivity> mActivityTestRule = new ActivityTestRule<>(CameraActivity.class);

    @Test
    public void cameraCaptureTest() {
        int prevCount = getPhotoCount();
        ViewInteraction button = onView(
                allOf(ViewMatchers.withId(R.id.picture), withText("Picture"),
                        withParent(withId(R.id.control)),
                        isDisplayed()));
        button.perform(click());
        int postCount = getPhotoCount();
        assertTrue("Error, number of photos did not increase by 1", postCount-1 == prevCount);
    }

}
