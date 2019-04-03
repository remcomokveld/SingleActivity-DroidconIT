package nl.remcomokveld.nhl.iap

sealed class SubscriptionState {
    object Unavailable : SubscriptionState()
    class SubscriptionAvailable(val price: String, val duration: String) : SubscriptionState()
    object Subscribed : SubscriptionState()
}
