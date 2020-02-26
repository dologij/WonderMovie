package com.brunix.wondermovie.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.brunix.wondermovie.R
import com.brunix.wondermovie.model.server.MovieDb
import com.brunix.wondermovie.ui.main.MovieListActivity
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.core.StringContains.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get

class UiTests : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MovieListActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant("android.permission.ACCESS_COARSE_LOCATION")

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson(ApplicationProvider.getApplicationContext(),
                "popular_movies.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<MovieDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun clickingAMovieNavigatesToDetail() {
        activityTestRule.launchActivity(null)

        onView(withId(R.id.movie_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )

        onView(withId(R.id.movie_detail_container))
            .check(matches(hasDescendant(withText(containsString("All unemployed")))))
    }
}