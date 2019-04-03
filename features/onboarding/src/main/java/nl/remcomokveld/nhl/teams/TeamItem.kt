package nl.remcomokveld.nhl.teams

import androidx.core.view.isVisible
import nl.remcomokveld.nhl.models.Team
import nl.remcomokveld.nhl.onboarding.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.team_item.*

data class TeamItem(
    private val team: Team,
    private val checked: Boolean,
    private val fragment: FollowTeamFragment
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.team_name.text = team.humanName
        viewHolder.check_mark.isVisible = checked
        viewHolder.itemView.setOnClickListener { fragment.onTeamSelected(team) }
    }

    override fun getLayout(): Int = R.layout.team_item

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean =
        other is TeamItem && other.team == team
}
