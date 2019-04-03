package nl.remcomokveld.nhl.iap

import android.app.Activity
import androidx.lifecycle.LiveData

interface IAPViewModel {
    val subscriptionState: LiveData<SubscriptionState>
    val isSubscribed: Boolean
    fun setupBillingClient()
    fun onSubscribeClicked(activity: Activity)
}
