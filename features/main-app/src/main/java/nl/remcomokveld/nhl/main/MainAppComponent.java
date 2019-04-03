package nl.remcomokveld.nhl.main;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

@Component(modules = MainAppModule.class)
public interface MainAppComponent extends AndroidInjector<MainAppFragment> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bindFragment(MainAppFragment fragment);

        MainAppComponent build();
    }
}
