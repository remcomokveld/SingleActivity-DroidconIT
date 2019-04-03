package nl.remcomokveld.nhl.iap

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import nl.remcomokveld.nhl.iap.SubscriptionState.Subscribed
import nl.remcomokveld.nhl.iap.SubscriptionState.SubscriptionAvailable
import nl.remcomokveld.nhl.iap.SubscriptionState.Unavailable

/**
 * ViewModel which will start a connection with a [BillingClient] when [setupBillingClient] and keep that active until
 * [onCleared] gets called.
 */
class GoogleIAPViewModel(application: Application) : AndroidViewModel(application), IAPViewModel {

    private val liveData = MutableLiveData<SubscriptionState>()

    override val subscriptionState: LiveData<SubscriptionState> = liveData

    private var currentSkuDetails: SkuDetails? = null
        set(value) {
            if (value != null) {
                liveData.postValue(value.toSubscriptionState())
            } else {
                liveData.postValue(Unavailable)
            }
            field = value
        }


    override val isSubscribed: Boolean
        get() = billingClient?.let { it.isReady && it.queryPurchases(BillingClient.SkuType.SUBS).purchasesList.any { it.sku == SKU } } == true

    private var billingClient: BillingClient? = null

    override fun setupBillingClient() {
        if (billingClient == null) {
            billingClient = BillingClient.newBuilder(getApplication())
                .setListener { _, _ -> if (isSubscribed) liveData.postValue(Subscribed) }
                .build()
                .apply {
                    startConnection(object : BillingClientStateListener {
                        override fun onBillingSetupFinished(responseCode: Int) {
                            if (isSubscribed) {
                                liveData.postValue(Subscribed)
                            } else {
                                querySkuDetailsAsync(skuDetailsParams) { _, skuDetailsList ->
                                    currentSkuDetails = skuDetailsList?.find { it.sku == SKU }
                                }
                            }
                        }

                        override fun onBillingServiceDisconnected() {
                            currentSkuDetails = null
                        }

                    })
                }
        }
    }

    override fun onSubscribeClicked(activity: Activity) {
        val subscriptionSku = currentSkuDetails
        if (subscriptionSku != null) {
            val billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(subscriptionSku)
                .build()
            billingClient?.launchBillingFlow(activity, billingFlowParams)
        }
    }

    override fun onCleared() {
        super.onCleared()
        billingClient?.endConnection()
    }

    private fun SkuDetails.toSubscriptionState() =
        SubscriptionAvailable(price, subscriptionPeriod)

    private companion object {
        const val SKU = "monthly_test_subscription"
        val skuDetailsParams: SkuDetailsParams = SkuDetailsParams.newBuilder()
            .setSkusList(listOf(SKU))
            .setType(BillingClient.SkuType.SUBS)
            .build()

    }
}
