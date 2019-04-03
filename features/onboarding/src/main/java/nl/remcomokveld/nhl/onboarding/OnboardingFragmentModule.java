package nl.remcomokveld.nhl.onboarding;

import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import nl.remcomokveld.nhl.iap.GoogleIAPViewModel;
import nl.remcomokveld.nhl.iap.IAPFragment;
import nl.remcomokveld.nhl.iap.IAPHost;
import nl.remcomokveld.nhl.iap.IAPViewModel;
import nl.remcomokveld.nhl.iap.SignUpFragment;
import nl.remcomokveld.nhl.paywall.PaywallFragment;
import nl.remcomokveld.nhl.paywall.PaywallHost;
import nl.remcomokveld.nhl.teams.FollowTeamFragment;
import nl.remcomokveld.nhl.teams.FollowTeamHost;

@Module(includes = AndroidSupportInjectionModule.class)
public abstract class OnboardingFragmentModule {

    @ContributesAndroidInjector
    abstract FollowTeamFragment followTeamFragment();

    @Binds
    abstract FollowTeamHost bindFollowTeamHost(OnboardingFragment fragment);

    @ContributesAndroidInjector
    abstract SignUpFragment signUpFragment();

    @ContributesAndroidInjector
    abstract IAPFragment iapFragment();

    @Binds
    abstract IAPHost bindIAPHost(OnboardingFragment fragment);

    @ContributesAndroidInjector
    abstract LogInFragment logInFragment();

    @Binds
    abstract LogInHost bindLogInHost(OnboardingFragment fragment);

    @ContributesAndroidInjector
    abstract PaywallFragment paywallFragment();

    @Binds
    abstract PaywallHost bindPaywallHost(OnboardingFragment fragment);

    @Provides
    static IAPViewModel provideIapViewModel(OnboardingFragment fragment) {
        return ViewModelProviders.of(fragment).get(GoogleIAPViewModel.class);
    }
}
