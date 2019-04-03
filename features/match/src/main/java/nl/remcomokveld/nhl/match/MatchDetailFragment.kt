package nl.remcomokveld.nhl.match

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.models.Match
import kotlinx.android.synthetic.main.match_detail_fragment.*

class MatchDetailFragment : DaggerUIFragment(R.layout.match_detail_fragment) {

    private val match: Match
        get() = arguments?.getParcelable("match")
            ?: throw IllegalArgumentException()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        home_team.text = match.homeTeam.humanName
        away_team.text = match.awayTeam.humanName
        score.text = getString(R.string.score_template, match.awayScore, match.homeScore)
    }

    companion object {
        fun newInstance(match: Match) = MatchDetailFragment()
            .apply { arguments = bundleOf("match" to match) }
    }
}
