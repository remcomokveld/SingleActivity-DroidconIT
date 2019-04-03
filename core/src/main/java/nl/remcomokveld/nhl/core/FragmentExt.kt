package nl.remcomokveld.nhl.core

import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.findParentThatImplements() = findParentThatImplements(T::class.java)

fun <T> Fragment.findParentThatImplements(type: Class<T>): T {
  return when {
    parentFragment != null && type.isAssignableFrom(requireParentFragment().javaClass) ->
      parentFragment as T
    parentFragment != null ->
      requireParentFragment().findParentThatImplements(type)
    activity != null && type.isAssignableFrom(requireActivity().javaClass) ->
      requireActivity() as T
    else -> throw IllegalStateException("None of the parents implement $type")
  }
}
