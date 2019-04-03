package nl.remcomokveld.nhl.myteam

import nl.remcomokveld.nhl.models.Match
import nl.remcomokveld.nhl.models.Team
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.match_item.*

class MatchItem(
    val match: Match,
    private val myTeam: Team
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            other_team.text = if (myTeam == match.homeTeam) match.awayTeam.humanName else match.homeTeam.humanName
            home_away.setText(if (myTeam == match.homeTeam) R.string.home else R.string.away)
            home_score.text = match.homeScore.toString()
            away_score.text = match.awayScore.toString()

        }
    }

    override fun getLayout(): Int = R.layout.match_item
}
