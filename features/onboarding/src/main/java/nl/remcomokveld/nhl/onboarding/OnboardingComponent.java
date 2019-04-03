package nl.remcomokveld.nhl.onboarding;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

@Component(modules = OnboardingFragmentModule.class)
public interface OnboardingComponent extends AndroidInjector<OnboardingFragment> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bindHost(OnboardingHost host);

        @BindsInstance
        Builder bindFragment(OnboardingFragment fragment);

        OnboardingComponent build();
    }
}
