package nl.remcomokveld.nhl.splash

import nl.remcomokveld.nhl.models.NHLAccount

interface SplashHost {
    fun onLoggedIn(account: NHLAccount)
    fun onLoggedOut()
}
