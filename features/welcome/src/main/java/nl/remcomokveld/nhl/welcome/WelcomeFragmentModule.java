package nl.remcomokveld.nhl.welcome;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WelcomeFragmentModule {

    @ContributesAndroidInjector
    abstract ThanksForSubscribingFragment thanksForSubscribingFragment();

    @ContributesAndroidInjector
    abstract LiveGamesFragment liveGamesFragment();
}
