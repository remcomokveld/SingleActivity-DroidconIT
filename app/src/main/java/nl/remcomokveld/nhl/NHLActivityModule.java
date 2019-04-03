package nl.remcomokveld.nhl;

import nl.remcomokveld.nhl.account.AccountDataSource;
import nl.remcomokveld.nhl.account.AccountRepository;
import nl.remcomokveld.nhl.splash.SplashFragment;
import nl.remcomokveld.nhl.splash.SplashHost;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class NHLActivityModule {

    @ContributesAndroidInjector
    abstract SplashFragment splashFragment();

    @Binds
    abstract AccountDataSource bindAccountDataSource(AccountRepository repository);

    @Binds
    abstract SplashHost bindSplashHost(NHLActivity activity);
}
