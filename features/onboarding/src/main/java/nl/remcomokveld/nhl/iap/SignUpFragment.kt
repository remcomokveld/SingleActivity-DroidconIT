package nl.remcomokveld.nhl.iap

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.email_password_fragment.*
import nl.remcomokveld.nhl.account.AccountViewModel
import nl.remcomokveld.nhl.core.DaggerUIFragment
import nl.remcomokveld.nhl.onboarding.LogInHost
import nl.remcomokveld.nhl.onboarding.R
import javax.inject.Inject

/**
 * Screen to sign up for a new account after having gone through IAP.
 */
class SignUpFragment : DaggerUIFragment(R.layout.email_password_fragment) {

    @Inject
    lateinit var host: LogInHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title_view.setText(R.string.sign_up)
        email_field.addTextChangedListener { invalidateSubscribeButton() }
        password_field.addTextChangedListener { invalidateSubscribeButton() }
        subscribe_button.setText(R.string.sign_up)
        subscribe_button.setOnClickListener {
            hideSoftInput()
            host.onLoggedIn(email_field.text.toString())
        }
    }

    private fun invalidateSubscribeButton() {
        subscribe_button.isEnabled = !email_field.text.isNullOrBlank() && !password_field.text.isNullOrBlank()
    }

    override fun onStart() {
        super.onStart()
        AccountViewModel.get(requireActivity()).setShowWelcomeScreen(true)
    }

    private fun hideSoftInput() {
        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}
