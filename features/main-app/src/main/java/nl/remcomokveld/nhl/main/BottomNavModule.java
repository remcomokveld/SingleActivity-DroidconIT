package nl.remcomokveld.nhl.main;

import nl.remcomokveld.nhl.match.MatchDetailFragment;
import nl.remcomokveld.nhl.myteam.MyTeamFragment;
import nl.remcomokveld.nhl.myteam.MyTeamHost;
import nl.remcomokveld.nhl.scoreboard.ScoreboardFragment;
import nl.remcomokveld.nhl.scoreboard.ScoreboardHost;
import nl.remcomokveld.nhl.settings.SettingsFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BottomNavModule {

    @ContributesAndroidInjector
    abstract MyTeamFragment myTeamFragment();

    @Binds
    abstract MyTeamHost bindMyTeamHost(BottomNavFragment fragment);

    @ContributesAndroidInjector
    abstract ScoreboardFragment scoreboardFragment();

    @Binds
    abstract ScoreboardHost bindScoreboardHost(BottomNavFragment fragment);

    @ContributesAndroidInjector
    abstract SettingsFragment settingsFragment();

    @ContributesAndroidInjector
    abstract MatchDetailFragment matchDetailFragment();

}
