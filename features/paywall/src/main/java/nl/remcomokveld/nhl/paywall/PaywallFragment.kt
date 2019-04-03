package nl.remcomokveld.nhl.paywall

import android.os.Bundle
import android.view.View
import nl.remcomokveld.nhl.core.DaggerUIFragment
import kotlinx.android.synthetic.main.paywall_fragment.*
import javax.inject.Inject

class PaywallFragment : DaggerUIFragment(R.layout.paywall_fragment) {

    @Inject
    lateinit var host: PaywallHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_rogers_button.setOnClickListener { host.startRogersLogin() }
        login_nhl_button.setOnClickListener { host.startNHLLogin() }
        sign_up_button.setOnClickListener { host.startIAP() }
    }
}
