package nl.remcomokveld.nhl.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.core.addToStack
import nl.remcomokveld.nhl.core.replaceStack
import kotlinx.android.synthetic.main.welcome_fragment.*
import javax.inject.Inject

open class WelcomeFragment : DaggerUIFragment(R.layout.welcome_fragment) {

    @Inject
    lateinit var host: WelcomeHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.addOnBackStackChangedListener {
            page_indicator.text = getString(R.string.page_indicator, childFragmentManager.backStackEntryCount + 1)
        }
        skipButton.setOnClickListener { host.onWelcomeDone() }
        if (savedInstanceState == null) {
            page_indicator.text = getString(R.string.page_indicator, 1)
            childFragmentManager.replaceStack(R.id.welcome_container, ThanksForSubscribingFragment())
        }
    }

    fun onNextClicked(fromFragment: Fragment) {
        when (fromFragment) {
            is ThanksForSubscribingFragment -> addToStack(LiveGamesFragment())
            is LiveGamesFragment -> host.onWelcomeDone()
        }
    }

    private fun addToStack(fragment: Fragment) = childFragmentManager.addToStack(R.id.welcome_container, fragment)
}
