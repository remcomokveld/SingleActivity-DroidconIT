package nl.remcomokveld.nhl.onboarding

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.core.StackEntry
import nl.remcomokveld.nhl.core.addToStack
import nl.remcomokveld.nhl.core.findParentThatImplements
import nl.remcomokveld.nhl.core.replaceStack
import nl.remcomokveld.nhl.iap.IAPFragment
import nl.remcomokveld.nhl.iap.IAPHost
import nl.remcomokveld.nhl.iap.IAPViewModel
import nl.remcomokveld.nhl.iap.SignUpFragment
import nl.remcomokveld.nhl.models.NHLAccount
import nl.remcomokveld.nhl.models.Team
import nl.remcomokveld.nhl.paywall.PaywallFragment
import nl.remcomokveld.nhl.paywall.PaywallHost
import nl.remcomokveld.nhl.teams.FollowTeamFragment
import nl.remcomokveld.nhl.teams.FollowTeamHost
import javax.inject.Inject

/**
 * Host to the entire onboarding process. Because the Onboarding flow has a somewhat complex back stack, and navigation
 * logic, this
 */
class OnboardingFragment : DaggerUIFragment(R.layout.onboarding_fragment),
    FollowTeamHost, LogInHost, PaywallHost, IAPHost {

    @Inject
    lateinit var host: OnboardingHost

    @Inject
    lateinit var iapViewModel: IAPViewModel

    private var favoriteTeam: Team? = null

    override fun injectSelf() =
        DaggerOnboardingComponent.builder()
            .bindFragment(this)
            .bindHost(findParentThatImplements())
            .build()
            .inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iapViewModel.setupBillingClient()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            replaceStack(FollowTeamFragment(), TAG_FOLLOW_TEAM)
        }
    }

    override fun onFavoriteTeamsSelected(team: Team) {
        favoriteTeam = team
        if (iapViewModel.isSubscribed) {
            replaceStack(
                childFragmentManager.findFragmentByTag(TAG_FOLLOW_TEAM) ?: FollowTeamFragment(),
                TAG_FOLLOW_TEAM,
                StackEntry(SignUpFragment())
            )
        } else {
            addToStack(PaywallFragment(), TAG_PAYWALL)
        }
    }

    override fun onSubscribed() {
        replaceStack(
            childFragmentManager.findFragmentByTag(TAG_FOLLOW_TEAM)
                ?: FollowTeamFragment(),
            TAG_FOLLOW_TEAM,
            StackEntry(SignUpFragment(), null)
        )
    }

    override fun startRogersLogin() =
        CustomTabsIntent.Builder().build()
            .launchUrl(
                requireContext(),
                Uri.parse("https://www.rogers.com/consumer/easyloginriverpage")
            )

    override fun startNHLLogin() = addToStack(LogInFragment(), TAG_LOG_IN)

    override fun startIAP() = addToStack(IAPFragment(), TAG_IAP)

    override fun onLoggedIn(email: String) {
        val favoriteTeam = favoriteTeam ?: throw IllegalStateException()
        val account = NHLAccount(email, favoriteTeam)
        host.onLoggedIn(account)
    }

    private fun replaceStack(fragment: Fragment, tag: String?, vararg stack: StackEntry) =
        childFragmentManager.replaceStack(R.id.onboarding_container, fragment, tag, *stack)

    private fun addToStack(fragment: Fragment, tag: String?) =
        childFragmentManager.addToStack(R.id.onboarding_container, fragment, tag)

    companion object {
        const val TAG_FOLLOW_TEAM = "FollowTeam"
        const val TAG_IAP = "TAG_IAP"
        const val TAG_LOG_IN = "LogIn"
        const val TAG_PAYWALL = "Paywall"
    }
}

