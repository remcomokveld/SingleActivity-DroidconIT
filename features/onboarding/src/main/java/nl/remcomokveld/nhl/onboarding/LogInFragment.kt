package nl.remcomokveld.nhl.onboarding

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import nl.remcomokveld.nhl.account.AccountViewModel
import nl.remcomokveld.nhl.core.DaggerUIFragment
import kotlinx.android.synthetic.main.email_password_fragment.*
import javax.inject.Inject


class LogInFragment : DaggerUIFragment(R.layout.email_password_fragment) {

    @Inject
    lateinit var host: LogInHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title_view.setText(R.string.log_in)
        subscribe_button.setText(R.string.log_in)
        email_field.addTextChangedListener { invalidateLogInButton() }
        password_field.addTextChangedListener { invalidateLogInButton() }
        subscribe_button.setOnClickListener {
            email_field.text?.let {email ->
                hideSoftInput()
                host.onLoggedIn(email.toString())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        AccountViewModel.get(requireActivity()).setShowWelcomeScreen(false)
    }

    private fun invalidateLogInButton() {
        subscribe_button.isEnabled = !(email_field.text.isNullOrBlank() || password_field.text.isNullOrBlank())
    }

    private fun hideSoftInput() {
        val inputManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}
