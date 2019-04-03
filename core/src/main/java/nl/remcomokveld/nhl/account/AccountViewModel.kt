package nl.remcomokveld.nhl.account

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get

/**
 * ViewModel for which one instance exists per activity to
 */
class AccountViewModel : ViewModel() {

    private var showWelcomeScreen: Boolean = false

    fun setShowWelcomeScreen(show: Boolean) {
        showWelcomeScreen = show
    }

    fun shouldShowWelcomeScreen() = showWelcomeScreen.also { showWelcomeScreen = false }


    companion object {
        fun get(activity: FragmentActivity): AccountViewModel =
            ViewModelProviders.of(activity).get()
    }
}
