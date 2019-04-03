package nl.remcomokveld.nhl.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.commit

fun FragmentManager.replaceStack(containerId: Int, fragment: Fragment, vararg stack: StackEntry) =
    replaceStack(containerId, fragment, null, *stack)

/**
 * Replace the entire stack of the receiving [FragmentManager] wih the [rootFragment] and optional extra fragments that
 * replace the
 */
fun FragmentManager.replaceStack(
    container: Int,
    rootFragment: Fragment,
    tag: String?,
    vararg stack: StackEntry
) {
    executePendingTransactions()
    if (backStackEntryCount > 0) {
        val rootBackStackEntry = getBackStackEntryAt(0)
        popBackStack(rootBackStackEntry.id, POP_BACK_STACK_INCLUSIVE)
    }
    replaceFragment(container, rootFragment, tag)
    for (entry in stack) {
        addToStack(container, entry.fragment, entry.tag, entry.name)
    }
}

fun FragmentManager.replaceFragment(container: Int, fragment: Fragment, tag: String? = null) {
    commit {
        replace(container, fragment, tag)
        setPrimaryNavigationFragment(fragment)
        setReorderingAllowed(true)
    }
}

fun FragmentManager.addToStack(container: Int, fragment: Fragment, tag: String? = null, backStackName: String? = null) {
    commit {
        replace(container, fragment, tag)
        setPrimaryNavigationFragment(fragment)
        setReorderingAllowed(true)
        addToBackStack(backStackName)
    }
}

data class StackEntry(val fragment: Fragment, val name: String? = null, val tag: String? = null)

fun FragmentManager.handleBackPressDepthFirst() =
    handleBackPressDepthFirst(primaryNavigationFragment)

private fun handleBackPressDepthFirst(fragment: Fragment?): Boolean {
    if (fragment == null) {
        return false
    }
    val child = fragment.childFragmentManager.primaryNavigationFragment
    if (child != null) {
        val handledInChild = handleBackPressDepthFirst(child)
        if (handledInChild) return true
    }
    if (fragment is BackHandlingFragment) {
        return fragment.onBackPressed()
    }
    return false
}
