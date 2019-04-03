package nl.remcomokveld.nhl.splash

import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import nl.remcomokveld.nhl.account.AccountDataSource
import nl.remcomokveld.nhl.core.DaggerUIFragment
import javax.inject.Inject

open class SplashFragment : DaggerUIFragment(R.layout.splash_fragment) {

    @Inject
    lateinit var host: SplashHost

    @Inject
    lateinit var accountDataSource: AccountDataSource

    override fun onStart() {
        super.onStart()
        accountDataSource.accountOnce()
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scope())
            .subscribe(
                { host.onLoggedIn(it) },
                { throw it },
                { host.onLoggedOut() }
            )
    }

}
