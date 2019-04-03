package nl.remcomokveld.nhl.teams

import nl.remcomokveld.nhl.models.Team

interface FollowTeamHost {
    fun onFavoriteTeamsSelected(team: Team)
}
