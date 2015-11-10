package ru.princeparadoxes.smsdelivery;

import android.support.test.rule.ActivityTestRule;
import ru.princeparadoxes.smsdelivery.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.princeparadoxes.smsdelivery.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = rule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }

    @Test
    public void testName() throws Exception {
    }

}
