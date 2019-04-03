package nl.remcomokveld.nhl.iap

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.content.edit
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.common.truth.Truth.assertThat
import nl.remcomokveld.nhl.onboarding.R
import org.junit.Test

class IAPFragmentTest {

    @Test
    fun buttonIsEnabledWhenBillingClientSetUp() {
        // Act
        launchFragmentInContainer<TestIAPFragment>()

        // Assert
        onView(withId(R.id.subscribe_button)).check(matches(isEnabled()))
    }

    @Test
    fun hostIsInvokedOnSubscribe() {
        val scenario = launchFragmentInContainer<TestIAPFragment>()

        onView(withId(R.id.subscribe_button)).check(matches(isEnabled())).perform(click())

        scenario.onFragment {
            assertThat((it.host as MockIAPHost).invocationCount).isEqualTo(1)
        }
    }

    class TestIAPFragment : IAPFragment() {
        override fun injectSelf() {
            this.host = MockIAPHost()
            this.iapViewModel = ViewModelProviders.of(this).get<MockIAPViewModel>()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            iapViewModel.setupBillingClient()
        }
    }

    class MockIAPHost : IAPHost {
        var invocationCount = 0
        override fun onSubscribed() {
            invocationCount++
        }
    }
}
