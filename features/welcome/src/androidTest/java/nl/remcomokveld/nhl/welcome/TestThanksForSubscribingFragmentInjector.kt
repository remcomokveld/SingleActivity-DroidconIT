package nl.remcomokveld.nhl.welcome

import dagger.android.AndroidInjector

class TestThanksForSubscribingFragmentInjector(private val welcomeFragment: WelcomeFragment) :
    AndroidInjector<ThanksForSubscribingFragment> {
    override fun inject(instance: ThanksForSubscribingFragment) {
        instance.welcomeFragment = welcomeFragment
    }
}
