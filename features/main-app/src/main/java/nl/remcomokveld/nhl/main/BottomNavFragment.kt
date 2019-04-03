package nl.remcomokveld.nhl.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.bottom_nav_fragment.*
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.core.StackEntry
import nl.remcomokveld.nhl.core.addToStack
import nl.remcomokveld.nhl.core.replaceStack
import nl.remcomokveld.nhl.match.MatchDetailFragment
import nl.remcomokveld.nhl.models.Match
import nl.remcomokveld.nhl.myteam.MyTeamFragment
import nl.remcomokveld.nhl.myteam.MyTeamHost
import nl.remcomokveld.nhl.scoreboard.ScoreboardFragment
import nl.remcomokveld.nhl.scoreboard.ScoreboardHost
import nl.remcomokveld.nhl.settings.SettingsFragment

class BottomNavFragment :
    DaggerUIFragment(R.layout.bottom_nav_fragment),
    FragmentManager.OnBackStackChangedListener,
    MyTeamHost,
    ScoreboardHost {

    private var navigationListenerEnabled: Boolean = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            replaceStack(MyTeamFragment(), TAG_MY_TEAM)
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            if (!navigationListenerEnabled) return@setOnNavigationItemSelectedListener true
            when (it.itemId) {
                R.id.scoreboard, R.id.my_team, R.id.settings -> replaceTopFragment(it)
                else -> return@setOnNavigationItemSelectedListener false
            }
            return@setOnNavigationItemSelectedListener true
        }
        bottom_navigation_view.setOnNavigationItemReselectedListener {
            if (!navigationListenerEnabled) return@setOnNavigationItemReselectedListener
            when (val fragment = childFragmentManager.primaryNavigationFragment) {
                is ScoreboardFragment -> fragment.refresh()
                is MyTeamFragment -> fragment.refresh()
                is SettingsFragment -> Unit
                else -> replaceTopFragment(it)
            }
        }
        childFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onDestroyView() {
        childFragmentManager.removeOnBackStackChangedListener(this)
        super.onDestroyView()
    }

    override fun onBackStackChanged() {
        navigationListenerEnabled = false
        when (childFragmentManager.primaryNavigationFragment) {
            is MyTeamFragment -> bottom_navigation_view.selectedItemId = R.id.my_team
            is ScoreboardFragment -> bottom_navigation_view.selectedItemId = R.id.scoreboard
            is SettingsFragment -> bottom_navigation_view.selectedItemId = R.id.settings
        }
        navigationListenerEnabled = true
    }

    private fun replaceTopFragment(item: MenuItem) {
        when (item.itemId) {
            R.id.my_team -> replaceStack(MyTeamFragment(), TAG_MY_TEAM)
            R.id.scoreboard -> startSecondaryTag(ScoreboardFragment())
            R.id.settings -> startSecondaryTag(SettingsFragment())
        }
    }

    private fun startSecondaryTag(fragment: Fragment) {
        replaceStack(
            childFragmentManager.findFragmentByTag(TAG_MY_TEAM) ?: MyTeamFragment(),
            TAG_MY_TEAM,
            fragment
        )
    }

    private fun replaceStack(rootFragment: Fragment, tag: String, vararg other: Fragment) {
        childFragmentManager.replaceStack(
            R.id.bottom_nav_content_container,
            rootFragment,
            tag,
            *(other.map { StackEntry(it) }.toTypedArray())
        )
    }

    override fun startMatchDetail(match: Match) {
        addToStack(MatchDetailFragment.newInstance(match))
    }

    private fun addToStack(fragment: Fragment) =
        childFragmentManager.addToStack(R.id.bottom_nav_content_container, fragment)

    companion object {
        const val TAG_MY_TEAM = "MyTeam"
    }
}
