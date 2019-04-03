package nl.remcomokveld.nhl.welcome

import android.os.Bundle
import android.view.View
import nl.remcomokveld.nhl.core.DaggerUIFragment
import kotlinx.android.synthetic.main.thanks_for_subscribing_fragment.*
import javax.inject.Inject

class ThanksForSubscribingFragment : DaggerUIFragment(R.layout.thanks_for_subscribing_fragment) {

    @Inject
    lateinit var welcomeFragment: WelcomeFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        continue_button.setOnClickListener { welcomeFragment.onNextClicked(fromFragment = this) }
    }

}
