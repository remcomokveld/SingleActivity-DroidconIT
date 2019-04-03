package nl.remcomokveld.nhl.account

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import nl.remcomokveld.nhl.models.NHLAccount
import nl.remcomokveld.nhl.models.Team
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(private val application: Application) : AccountDataSource {

    var accountCache: NHLAccount? = null

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(application)

    override fun accountOnce(): Maybe<NHLAccount> =
        Maybe.fromCallable<NHLAccount> {
            val email = preferences.getString(KEY_EMAIL, null)
            val team = preferences.getString(KEY_TEAM, null)
            return@fromCallable if (email != null && team != null) {
                NHLAccount(email, favoriteTeam = Team.valueOf(team))
            } else {
                null
            }
        }.doOnSuccess { accountCache = it }

    fun storeAccount(account: NHLAccount) = preferences.edit {
        putString(KEY_EMAIL, account.email)
        putString(KEY_TEAM, account.favoriteTeam.name)
    }.also { accountCache = account }

    private companion object {
        const val KEY_EMAIL = "email"
        const val KEY_TEAM = "team"
    }
}
