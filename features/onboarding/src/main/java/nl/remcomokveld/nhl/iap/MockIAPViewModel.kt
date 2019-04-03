package nl.remcomokveld.nhl.iap

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nl.remcomokveld.nhl.iap.SubscriptionState.Subscribed
import nl.remcomokveld.nhl.iap.SubscriptionState.SubscriptionAvailable

class MockIAPViewModel(application: Application) : AndroidViewModel(application), IAPViewModel {

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(getApplication())

    private val liveData = MutableLiveData<SubscriptionState>()

    override val subscriptionState: LiveData<SubscriptionState> = liveData

    override var isSubscribed: Boolean = subscriptionState.value == Subscribed

    override fun setupBillingClient() {
        if (preferences.getBoolean("subscribed", false)) {
            liveData.postValue(Subscribed)
        } else {
            liveData.postValue(SubscriptionAvailable("$3.99", "Month"))
        }
    }

    override fun onSubscribeClicked(activity: Activity) {
        preferences.edit { putBoolean("subscribed", true) }
        liveData.postValue(Subscribed)
    }
}
