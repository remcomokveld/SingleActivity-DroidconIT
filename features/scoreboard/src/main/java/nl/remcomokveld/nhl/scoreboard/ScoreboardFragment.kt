package nl.remcomokveld.nhl.scoreboard

import android.os.Bundle
import android.view.View
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.models.Match
import nl.remcomokveld.nhl.models.Team
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.scoreboard_fragment.*
import javax.inject.Inject
import kotlin.random.Random

class ScoreboardFragment : DaggerUIFragment(R.layout.scoreboard_fragment) {

    @Inject
    lateinit var host: ScoreboardHost

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.update(generateRandomMatches(10).map { ScoreboardMatchItem(it) })
        adapter.setOnItemClickListener { item, _ ->
            host.startMatchDetail((item as ScoreboardMatchItem).match)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = adapter

    }

    private fun generateRandomMatches(count: Int): List<Match> {
        return (0 until count).map {
            val homeTeam = Team.values()[Random.nextInt(Team.values().size)]
            val awayTeam = Team.values()[Random.nextInt(Team.values().size)]
            Match(
                homeTeam,
                awayTeam,
                Random.nextInt(10),
                Random.nextInt(10)
            )
        }
    }

    fun refresh() {
        recycler_view.smoothScrollToPosition(0)
    }
}
