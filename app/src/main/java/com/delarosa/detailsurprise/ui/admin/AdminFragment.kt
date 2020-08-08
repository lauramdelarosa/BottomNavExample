package com.delarosa.detailsurprise.ui.admin

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delarosa.detailsurprise.R
import kotlinx.android.synthetic.main.fragment_admin.*

class AdminFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        bottom_navigation_view?.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_company -> {
                    val fragment = AdminCompanyFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.action_message -> {
                    val fragment = AdminMessageFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                else -> false
            }
        }
        bottom_navigation_view.selectedItemId = R.id.action_message
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.main_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}


