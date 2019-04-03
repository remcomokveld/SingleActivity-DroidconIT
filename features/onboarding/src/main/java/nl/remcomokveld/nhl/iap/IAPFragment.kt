package nl.remcomokveld.nhl.iap

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.iap_fragment.*
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.iap.SubscriptionState.Subscribed
import nl.remcomokveld.nhl.iap.SubscriptionState.SubscriptionAvailable
import nl.remcomokveld.nhl.iap.SubscriptionState.Unavailable
import nl.remcomokveld.nhl.onboarding.R
import javax.inject.Inject

open class IAPFragment : DaggerUIFragment(R.layout.iap_fragment) {

    @Inject
    lateinit var iapViewModel: IAPViewModel

    @Inject
    lateinit var host: IAPHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iapViewModel.subscriptionState.observe(viewLifecycleOwner, Observer { onStateChanged(it) })
        subscribe_button.setOnClickListener { iapViewModel.onSubscribeClicked(requireActivity()) }
    }

    private fun onStateChanged(state: SubscriptionState) {
        when (state) {
            Subscribed -> host.onSubscribed()
            Unavailable -> {
                subscribe_button.isEnabled = false
                subscribe_button.setText(R.string.play_not_available)
            }
            is SubscriptionAvailable -> {
                subscribe_button.isEnabled = true
                subscribe_button.text = getString(R.string.subscribe_button, state.price, state.duration)
            }
        }
    }
}
