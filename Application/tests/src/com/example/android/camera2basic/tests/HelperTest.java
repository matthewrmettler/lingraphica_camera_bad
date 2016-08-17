package com.example.android.camera2basic.tests;

import android.os.Environment;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.android.camera2basic.CameraActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static com.example.android.camera2basic.Helper.createNewFile;
import static junit.framework.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HelperTest {

    @Rule
    public ActivityTestRule<CameraActivity> mActivityTestRule = new ActivityTestRule<>(CameraActivity.class);

    @Test
    public void createNewFileTest() {
        File picFile = createNewFile(mActivityTestRule.getActivity());
        assertTrue(picFile != null);
    }
}
