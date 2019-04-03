package nl.remcomokveld.nhl.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import nl.remcomokveld.nhl.models.NHLAccount
import nl.remcomokveld.nhl.models.Team
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainAppFragmentTest {

    @Test
    fun showWelcomeWhenFirstSignIn() {
        // Arrange
        val nhlAccount = NHLAccount(email = "stubEmail", favoriteTeam = Team.ANAHEIM_DUCKS)
        val arguments = MainAppFragment.createArguments(nhlAccount, true)

        // Act
        launchFragmentInContainer<MainAppFragment>(arguments, R.style.AppTheme)

        // Assert
        onView(withText(R.string.thanks_for_subscribing)).check(matches(isDisplayed()))
    }

    @Test
    fun showBottomTabsWhenNotFirstSignIn() {
        // Arrange
        val nhlAccount = NHLAccount(email = "stubEmail", favoriteTeam = Team.ANAHEIM_DUCKS)
        val arguments = MainAppFragment.createArguments(nhlAccount, false)

        // Act
        launchFragmentInContainer<MainAppFragment>(arguments, R.style.AppTheme)

        // Assert
        onView(allOf(withText(R.string.menu_home), isDisplayed()))
            .check(matches(isSelected()))
        onView(allOf(withText(R.string.menu_teams), isDisplayed()))
            .check(matches(allOf(not(isSelected()))))
    }
}
