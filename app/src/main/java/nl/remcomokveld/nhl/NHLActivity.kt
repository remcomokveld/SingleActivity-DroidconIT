package nl.remcomokveld.nhl

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerAppCompatActivity
import nl.remcomokveld.nhl.account.AccountRepository
import nl.remcomokveld.nhl.account.AccountViewModel
import nl.remcomokveld.nhl.core.handleBackPressDepthFirst
import nl.remcomokveld.nhl.core.replaceFragment
import nl.remcomokveld.nhl.main.MainAppFragment
import nl.remcomokveld.nhl.models.NHLAccount
import nl.remcomokveld.nhl.onboarding.OnboardingFragment
import nl.remcomokveld.nhl.onboarding.OnboardingHost
import nl.remcomokveld.nhl.splash.SplashFragment
import nl.remcomokveld.nhl.splash.SplashHost
import javax.inject.Inject

class NHLActivity : DaggerAppCompatActivity(), SplashHost, OnboardingHost {

    @Inject
    lateinit var accountRepository: AccountRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            accountRepository.accountCache.let { account ->
                if (account == null) {
                    replaceFragment(SplashFragment())
                } else {
                    onLoggedIn(account)
                }
            }
        }
    }

    override fun onLoggedIn(account: NHLAccount) {
        accountRepository.storeAccount(account)
        replaceFragment(MainAppFragment.newInstance(account, AccountViewModel.get(this).shouldShowWelcomeScreen()))
    }

    override fun onLoggedOut() =
        replaceFragment(OnboardingFragment())

    private fun replaceFragment(fragment: Fragment) =
        supportFragmentManager.replaceFragment(R.id.main_activity_container, fragment)

    override fun onBackPressed() {
        val handled = supportFragmentManager.handleBackPressDepthFirst()
        if (!handled)
            super.onBackPressed()
    }
}
