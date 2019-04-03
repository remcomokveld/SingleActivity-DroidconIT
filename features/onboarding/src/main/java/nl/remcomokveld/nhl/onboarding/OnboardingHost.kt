package nl.remcomokveld.nhl.onboarding

import nl.remcomokveld.nhl.models.NHLAccount

interface OnboardingHost {
    fun onLoggedIn(account: NHLAccount)
}
