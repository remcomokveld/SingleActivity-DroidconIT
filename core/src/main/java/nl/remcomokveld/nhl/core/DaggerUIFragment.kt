package nl.remcomokveld.nhl.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class DaggerUIFragment(@LayoutRes private val layoutId: Int) : Fragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context) {
        injectSelf()
        super.onAttach(context)
    }

    /**
     * By making this protected it becomes possible to not automatically inject from the parent fragment and let
     * this fragment have it's own component.
     */
    protected open fun injectSelf() = AndroidSupportInjection.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutId, container, false)

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector
}
