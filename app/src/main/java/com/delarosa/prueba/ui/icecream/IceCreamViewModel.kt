package com.delarosa.prueba.ui.icecream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delarosa.data.ResultData
import com.delarosa.domain.IceCream
import com.delarosa.prueba.ui.common.ScopedViewModel
import com.delarosa.prueba.ui.uientities.Purchase
import com.delarosa.usecases.GetIceCream
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class IceCreamViewModel(
    val getIceCream: GetIceCream,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    private var purchase: Purchase? = null
    private var orderList = mutableListOf<IceCream>()

    init {
        initServiceCall()
    }

    private fun initServiceCall() {
        launch {
            when (val result = getIceCream.invoke()) {
                is ResultData.Success -> {
                    _model.value = UiModel.Content(result.data)
                }
                is ResultData.Error -> {
                    result.exception.toString()
                }
            }
        }
    }

    fun onItemClicked(iceCream: IceCream) {
        validateItem(iceCream)
        purchase = Purchase(orderList.toList(), getTotal(orderList))
        _model.value = UiModel.ItemClicked(iceCream.name1)
    }

    fun order() {
        purchase?.let { _model.value = UiModel.Navigate(it) }
    }

    private fun getTotal(orderList: List<IceCream>): Float {
        return orderList.map { it.price.removePrefix("$").toFloat() }.sum().round(2)
    }

    private fun validateItem(iceCream: IceCream) {
        val validatedList = mutableListOf<IceCream>()
        val setElement = HashSet<IceCream>()
        orderList.forEach { setElement.add(it) }
        for (repeated in orderList) {
            if (setElement.contains(repeated)) validatedList.add(repeated)
        }
        if (validatedList.size > 1) orderList.removeAll { it -> it == iceCream }
        else orderList.add(iceCream)
    }

    private fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val iceCream: List<IceCream>) : UiModel()
        class ItemClicked(val name: String) : UiModel()
        class Navigate(val purchase: Purchase) : UiModel()
    }
}