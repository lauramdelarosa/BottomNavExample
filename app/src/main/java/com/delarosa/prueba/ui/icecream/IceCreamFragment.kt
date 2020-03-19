package com.delarosa.prueba.ui.icecream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.delarosa.prueba.R
import com.delarosa.prueba.ui.bill.BillFragment.Companion.PURCHASE
import com.delarosa.prueba.ui.icecream.IceCreamViewModel.UiModel
import kotlinx.android.synthetic.main.fragment_ice_cream.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class IceCreamFragment : Fragment() {

    private lateinit var adapter: IceCreamAdapter
    private val viewModel: IceCreamViewModel by currentScope.viewModel(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ice_cream, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = IceCreamAdapter(viewModel::onItemClicked)
        recycler?.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
        order?.setOnClickListener { viewModel.order() }
    }

    private fun updateUi(model: UiModel) {
        progress?.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is UiModel.Content -> adapter.appendItems(model.iceCream)
            is UiModel.ItemClicked -> {
                //todo update selected rectangle
                Toast.makeText(
                    context,
                    "${model.name} selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is UiModel.Navigate -> {
                val bundle = Bundle()
                bundle.putSerializable(PURCHASE, model.purchase)
                findNavController().navigate(R.id.action_ice_cream_to_ice_bill, bundle)
            }
        }
    }


}