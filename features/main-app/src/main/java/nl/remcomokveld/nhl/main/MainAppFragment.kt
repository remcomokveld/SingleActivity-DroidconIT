package nl.remcomokveld.nhl.main

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.core.StackEntry
import nl.remcomokveld.nhl.core.replaceStack
import nl.remcomokveld.nhl.models.NHLAccount
import nl.remcomokveld.nhl.welcome.WelcomeFragment
import nl.remcomokveld.nhl.welcome.WelcomeHost

class MainAppFragment : DaggerUIFragment(R.layout.main_app_fragment), WelcomeHost {


    val account: NHLAccount
        get() = arguments?.getParcelable("account")
            ?: throw IllegalArgumentException("'account' must be specified")

    override fun injectSelf() =
        DaggerMainAppComponent.builder()
            .bindFragment(this)
            .build()
            .inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            if (arguments?.getBoolean("showWelcome") == true) {
                childFragmentManager.replaceStack(
                    R.id.main_app_container,
                    BottomNavFragment(),
                    StackEntry(WelcomeFragment(), "Welcome")
                )
            } else {
                childFragmentManager.replaceStack(
                    R.id.main_app_container, BottomNavFragment()
                )
            }
        }
    }

    override fun onWelcomeDone() = childFragmentManager.popBackStack("Welcome", POP_BACK_STACK_INCLUSIVE)

    companion object {
        fun newInstance(account: NHLAccount, showWelcome: Boolean) = MainAppFragment()
            .apply { arguments = createArguments(account, showWelcome) }

        @VisibleForTesting
        internal fun createArguments(account: NHLAccount, showWelcome: Boolean) =
            bundleOf("account" to account, "showWelcome" to showWelcome)
    }
}
