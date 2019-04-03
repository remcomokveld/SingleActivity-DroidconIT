package nl.remcomokveld.nhl.splash

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get

inline fun <reified T : ViewModel> Fragment.getOrCreateViewModel(crossinline factory: () -> T): T {
    return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T2 : ViewModel?> create(modelClass: Class<T2>): T2 {
            @Suppress("UNCHECKED_CAST")
            return factory.invoke() as T2
        }
    }).get()
}
