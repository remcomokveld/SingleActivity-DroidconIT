package nl.remcomokveld.nhl.main;

import nl.remcomokveld.nhl.models.NHLAccount;
import nl.remcomokveld.nhl.welcome.WelcomeFragment;
import nl.remcomokveld.nhl.welcome.WelcomeFragmentModule;
import nl.remcomokveld.nhl.welcome.WelcomeHost;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = AndroidSupportInjectionModule.class)
public abstract class MainAppModule {

    @Provides
    static NHLAccount provideAccount(MainAppFragment fragment) {
        return fragment.getAccount();
    }

    @ContributesAndroidInjector(modules = BottomNavModule.class)
    abstract BottomNavFragment bottomNavFragment();

    @ContributesAndroidInjector(modules = WelcomeFragmentModule.class)
    abstract WelcomeFragment welcomeFragment();

    @Binds
    abstract WelcomeHost bindWelcomeHost(MainAppFragment fragment);
}
