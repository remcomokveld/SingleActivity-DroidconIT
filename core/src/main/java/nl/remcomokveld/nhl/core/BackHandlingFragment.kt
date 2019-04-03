package nl.remcomokveld.nhl.core

/**
 * Interface which can be implemented by any fragment that is on the Primary Navigation Tree to
 * intercept the back button being pressed.
 */
interface BackHandlingFragment {
    fun onBackPressed(): Boolean
}
