package com.delarosa.detailsurprise.ui.auth

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.delarosa.detailsurprise.R
import kotlinx.android.synthetic.main.fragment_auth.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment : Fragment() {

    private val viewModel: AuthViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        observers()

        seeSurpriseButton?.setOnClickListener { viewModel.buttonClicked(numberEditText?.text.toString()) }
        invisibleAdmin?.setOnClickListener {
            viewModel.invisibleAdminClicked()
        }
        numberEditText?.showKeyboard()
        numberEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when (s?.length) {
                    0 -> {
                        first?.text = ""
                        second?.text = ""
                        third?.text = ""
                        four?.text = ""
                        seeSurpriseButton?.isEnabled = false
                    }
                    1 -> {
                        first?.text = s[0].toString()
                        second?.text = ""
                        third?.text = ""
                        four?.text = ""
                        seeSurpriseButton?.isEnabled = false
                    }
                    2 -> {
                        first?.text = s[0].toString()
                        second?.text = s[1].toString()
                        third?.text = ""
                        four?.text = ""
                        seeSurpriseButton?.isEnabled = false
                    }
                    3 -> {
                        first?.text = s[0].toString()
                        second?.text = s[1].toString()
                        third?.text = s[2].toString()
                        four?.text = ""
                        seeSurpriseButton?.isEnabled = false
                    }
                    4 -> {
                        first?.text = s[0].toString()
                        second?.text = s[1].toString()
                        third?.text = s[2].toString()
                        four?.text = s[3].toString()
                        seeSurpriseButton?.isEnabled = true
                    }
                }
            }
        })
    }

    private fun updateUi(model: AuthViewModel.UiModel) {
        when (model) {
            is AuthViewModel.UiModel.ShowError -> {
                Toast.makeText(
                    context,
                    model.error,
                    Toast.LENGTH_SHORT
                )
                    .show()
                invalidCode?.visibility = View.VISIBLE
                numberEditText?.setText("")
                numberEditText?.showKeyboard()
            }
        }
    }

    private fun EditText.showKeyboard() {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun observers() {
        viewModel.model.observe(this, Observer(::updateUi))

        viewModel.navigationMessage.observe(this, Observer { event ->
            event.getContentIfNotHandled()
                ?.let {
                    findNavController().navigate(R.id.action_auth_to_message)
                }
        })
        viewModel.navigationAdmin.observe(this, Observer { event ->
            event.getContentIfNotHandled()
                ?.let {
                    findNavController().navigate(R.id.action_auth_to_admin)
                }
        })
    }
}
