package nl.remcomokveld.nhl.splash

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import io.reactivex.Maybe
import nl.remcomokveld.nhl.account.AccountDataSource
import nl.remcomokveld.nhl.models.NHLAccount
import nl.remcomokveld.nhl.models.Team
import org.junit.Test

class SplashFragmentTest {

    @Test
    fun noAccountInvokesLoggedOut() {
        val scenario = launchFragmentInContainer<TestSplashFragment>(bundleOf("account" to false))
        scenario.onFragment {
            assert((it.host as MockSplashHost).numLoggedOutInvocations == 1)
        }
    }

    @Test
    fun hasAccountInvokesLoggedIn() {
        val scenario = launchFragmentInContainer<TestSplashFragment>(bundleOf("account" to false))
        scenario.onFragment {
            val host = it.host as MockSplashHost
            assert(host.numLoggedOutInvocations == 0)
            assert(host.numLoggedInInvocations == 1)
            assert(host.account == NHLAccount("testEmail", Team.ANAHEIM_DUCKS))
        }
    }


    class TestSplashFragment : SplashFragment() {
        override fun injectSelf() {
            if (arguments?.getBoolean("account") == true) {
                accountDataSource = MockAccountDataSource(NHLAccount("testEmail", Team.ANAHEIM_DUCKS))
            } else {
                accountDataSource = MockAccountDataSource(null)
            }
            host = MockSplashHost()
        }
    }

    class MockAccountDataSource(private val account: NHLAccount?) : AccountDataSource {
        override fun accountOnce(): Maybe<NHLAccount> = Maybe.fromCallable<NHLAccount> { account }
    }

    class MockSplashHost : SplashHost {
        var account: NHLAccount? = null
        var numLoggedInInvocations = 0
        var numLoggedOutInvocations = 0

        override fun onLoggedIn(account: NHLAccount) {
            this.account = account
            numLoggedInInvocations++
        }

        override fun onLoggedOut() {
            numLoggedOutInvocations++
        }

    }
}
