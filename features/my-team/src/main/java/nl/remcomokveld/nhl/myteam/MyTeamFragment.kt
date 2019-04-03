package nl.remcomokveld.nhl.myteam

import android.os.Bundle
import android.view.View
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.models.Match
import nl.remcomokveld.nhl.models.NHLAccount
import nl.remcomokveld.nhl.models.Team
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.my_team_fragment.*
import javax.inject.Inject
import kotlin.random.Random

class MyTeamFragment : DaggerUIFragment(R.layout.my_team_fragment) {

    @Inject
    lateinit var host: MyTeamHost

    @Inject
    lateinit var nhlAccount: NHLAccount

    private var matches: List<Match> = emptyList()

    private val adapter = GroupAdapter<ViewHolder>()

    private val myTeam: Team get() = nhlAccount.favoriteTeam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matches = savedInstanceState?.getParcelableArrayList("matches") ?: generateRandomMatches(10)
        adapter.update(matches.map { MatchItem(it, myTeam) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        team_name.text = myTeam.humanName
        recycler_view.adapter = adapter
        adapter.setOnItemClickListener { item, _ ->
            host.startMatchDetail((item as MatchItem).match)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("matches", ArrayList(matches))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    private fun generateRandomMatches(count: Int): List<Match> {
        return (0 until count).map {
            Team.values()[Random.nextInt(
                Team.values().size
            )]
        }
            .filter { it != myTeam }
            .map {
                val home = Random.nextBoolean()
                val homeTeam = if (home) myTeam else it
                val awayTeam = if (home) it else myTeam
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
