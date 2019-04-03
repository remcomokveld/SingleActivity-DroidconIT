package nl.remcomokveld.nhl

import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class NHLApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerNHLApplicationComponent.builder().bindApplication(this).build()

    override fun onCreate() {
        super.onCreate()
        FragmentManager.enableDebugLogging(true)
    }
}
