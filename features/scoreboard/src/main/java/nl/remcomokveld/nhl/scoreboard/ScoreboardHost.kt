package nl.remcomokveld.nhl.scoreboard

import nl.remcomokveld.nhl.models.Match

interface ScoreboardHost {
    fun startMatchDetail(match: Match)
}
