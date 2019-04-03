package nl.remcomokveld.nhl.welcome

import android.os.Bundle
import android.view.View
import nl.remcomokveld.nhl.core.DaggerUIFragment
import kotlinx.android.synthetic.main.live_games_fragment.*
import javax.inject.Inject

class LiveGamesFragment : DaggerUIFragment(R.layout.live_games_fragment) {

    @Inject
    lateinit var welcomeFragment: WelcomeFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        continue_button.setOnClickListener { welcomeFragment.onNextClicked(fromFragment = this) }
    }
}
