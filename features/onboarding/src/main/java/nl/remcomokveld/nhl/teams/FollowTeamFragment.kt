package nl.remcomokveld.nhl.teams

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.follow_team_fragment.*
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.models.Team
import nl.remcomokveld.nhl.onboarding.R
import javax.inject.Inject

class FollowTeamFragment : DaggerUIFragment(R.layout.follow_team_fragment) {

    @Inject
    lateinit var host: FollowTeamHost

    private val viewModel: FollowTeamViewModel
        get() = ViewModelProviders.of(parentFragment!!).get()

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invalidateAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = adapter
        continue_button.setOnClickListener {
            host.onFavoriteTeamsSelected(viewModel.requireSelectedTeam())
        }
        invalidateDoneButton()
    }

    fun onTeamSelected(team: Team) {
        viewModel.onTeamSelected(team)
        invalidateDoneButton()
        invalidateAdapter()
    }

    private fun invalidateDoneButton() {
        continue_button.isEnabled = viewModel.hasTeamSelected()
    }

    private fun invalidateAdapter() {
        adapter.update(Team.values().map { TeamItem(it, viewModel.isSelected(it), this) })
    }
}
