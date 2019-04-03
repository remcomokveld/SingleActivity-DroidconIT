package nl.remcomokveld.nhl.welcome

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test

class WelcomeFragmentTest {

    @Test
    fun happyFlow() {
        val scenario = launchFragmentInContainer<TestWelcomeFragment>(themeResId = R.style.AppTheme)

        onView(withText(R.string.thanks_for_subscribing)).check(matches(isDisplayed()))
        onView(withId(R.id.page_indicator)).check(matches(withText("1/2")))

        onView(withText(R.string.continue_btn)).perform(click())

        onView(withText(R.string.watch_all_live)).check(matches(isDisplayed()))
        onView(withId(R.id.page_indicator)).check(matches(withText("2/2")))
        onView(withText(R.string.start_using_app_btn)).perform(click())

        scenario.onFragment {
            assert((it.host as MockWelcomeHost).numInvocations == 1)
        }
    }

    @Test
    fun skipButton() {
        val scenario = launchFragmentInContainer<TestWelcomeFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.skipButton)).perform(click())

        scenario.onFragment {
            assert((it.host as MockWelcomeHost).numInvocations == 1)
        }
    }

    class TestWelcomeFragment : WelcomeFragment() {
        override fun injectSelf() = TestWelcomeAndroidInjector().inject(this)
    }
}
