package nl.remcomokveld.nhl.welcome

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

class TestWelcomeAndroidInjector :
    AndroidInjector<WelcomeFragmentTest.TestWelcomeFragment> {

    override fun inject(instance: WelcomeFragmentTest.TestWelcomeFragment) {
        instance.host = MockWelcomeHost()
        val thanksFroSubscribingInjector =
            TestThanksForSubscribingFragmentInjector(instance)
        val liveGamesFragmentInjector =
            TestLiveGamesFragmentInjector(instance)
        instance.childFragmentInjector =
            DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(
                mapOf<Class<*>, Provider<AndroidInjector.Factory<*>>>(
                    ThanksForSubscribingFragment::class.java to Provider<AndroidInjector.Factory<*>> { AndroidInjector.Factory<ThanksForSubscribingFragment> { thanksFroSubscribingInjector } },
                    LiveGamesFragment::class.java to Provider<AndroidInjector.Factory<*>> { AndroidInjector.Factory<LiveGamesFragment> { liveGamesFragmentInjector } }
                ),
                emptyMap()
            )
    }
}
