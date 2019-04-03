package nl.remcomokveld.nhl;

import android.app.Application;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = AndroidSupportInjectionModule.class)
abstract class NHLApplicationModule {
    @Binds
    abstract Application bindApplication(NHLApplication application);

    @ContributesAndroidInjector(modules = NHLActivityModule.class)
    abstract NHLActivity nhlActivity();
}
