package nl.remcomokveld.nhl.welcome

class MockWelcomeHost : WelcomeHost {

    var numInvocations = 0
    
    override fun onWelcomeDone() {
        numInvocations++
    }
}
