package nl.remcomokveld.nhl.myteam

import nl.remcomokveld.nhl.models.Match

interface MyTeamHost {
    fun startMatchDetail(match: Match)
}
