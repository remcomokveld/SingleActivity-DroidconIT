package nl.remcomokveld.nhl.scoreboard

import nl.remcomokveld.nhl.models.Match
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.scoreboard_match_item.*

class ScoreboardMatchItem(internal val match: Match) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) = with(viewHolder) {
        home_team.text = match.homeTeam.humanName
        away_team.text = match.awayTeam.humanName
        home_score.text = match.homeScore.toString()
        away_score.text = match.awayScore.toString()
    }

    override fun getLayout(): Int = R.layout.scoreboard_match_item

}
