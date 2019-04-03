package nl.remcomokveld.nhl.welcome

import dagger.android.AndroidInjector

class TestLiveGamesFragmentInjector(private val welcomeFragment: WelcomeFragment) :
    AndroidInjector<LiveGamesFragment> {
    override fun inject(instance: LiveGamesFragment) {
        instance.welcomeFragment = welcomeFragment
    }
}
