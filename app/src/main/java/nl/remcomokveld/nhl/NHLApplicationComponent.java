package nl.remcomokveld.nhl;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = NHLApplicationModule.class)
public interface NHLApplicationComponent extends AndroidInjector<NHLApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bindApplication(NHLApplication application);

        NHLApplicationComponent build();
    }
}
