package nl.remcomokveld.nhl.teams

import androidx.lifecycle.ViewModel
import nl.remcomokveld.nhl.models.Team

/**
 * ViewModel which lives at the activity level, keeping track of the user's favorite team.
 */
class FollowTeamViewModel : ViewModel() {

    private var selectedTeam: Team? = null

    fun onTeamSelected(team: Team) {
        selectedTeam = team
    }

    fun isSelected(team: Team): Boolean = selectedTeam == team

    fun requireSelectedTeam(): Team =
        selectedTeam ?: throw IllegalStateException("No favorite team selected")

    fun hasTeamSelected() = selectedTeam != null
}
