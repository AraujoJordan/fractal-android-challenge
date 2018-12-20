package com.araujo.jordan.fractalbeer

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.araujo.jordan.fractalbeer.ui.beerList.BeerListActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class JobListTest {

    @Rule
    var mActivityRule: ActivityTestRule<BeerListActivity> = ActivityTestRule(BeerListActivity::class.java)

    @Test
    fun checkLoad() {
        onView(withId(R.id.beerlist_recyleview)).perform(scrollTo(), click())
        assertEquals(true, true)
    }


}
