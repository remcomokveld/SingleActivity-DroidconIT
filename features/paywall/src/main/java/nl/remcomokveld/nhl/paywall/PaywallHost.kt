package nl.remcomokveld.nhl.paywall

interface PaywallHost {
    fun startRogersLogin()
    fun startNHLLogin()
    fun startIAP()
}
