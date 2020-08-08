package com.delarosa.detailsurprise.ui.admin

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delarosa.detailsurprise.R
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class AdminMessageFragment : Fragment() {

    private val viewModel: AdminMessageViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return inflater.inflate(R.layout.fragment_admin_message, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


    }


    companion object {
        fun newInstance() = AdminMessageFragment()
    }
}


