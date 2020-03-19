package com.delarosa.prueba.ui.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.delarosa.prueba.R
import com.delarosa.prueba.ui.uientities.Purchase
import kotlinx.android.synthetic.main.fragment_bill.*

class BillFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val purchase = it.getSerializable(PURCHASE) as Purchase
            bill_resume?.text = purchase.toString()
        }
        newOrder?.setOnClickListener {
            findNavController().navigate(R.id.action_bill_to_ice_cream, null)
        }
    }

    companion object {
        const val PURCHASE = "purchase"
    }
}