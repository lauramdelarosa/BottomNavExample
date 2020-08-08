package com.delarosa.detailsurprise.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delarosa.data.ResultData
import com.delarosa.detailsurprise.ui.common.Event
import com.delarosa.detailsurprise.ui.common.ScopedViewModel
import com.delarosa.domain.DetailSurprise
import com.delarosa.usecases.SendDetailSurprise
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AdminMessageViewModel(
    private val sendDetailSurprise: SendDetailSurprise,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {


    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model


    fun buttonClicked(detailSurprise: DetailSurprise) {
        launch {
            when (val result = sendDetailSurprise.invoke(detailSurprise)) {
                is ResultData.Success -> {
                    _model.value = UiModel.ShowComplete("completado")
                }
                is ResultData.Error -> {
                    val error = result.exception.toString()
                    _model.value = UiModel.ShowError(error)
                }
            }
        }
    }


    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        class ShowError(val error: String) : UiModel()
        class ShowComplete(val message: String) : UiModel()
    }


}
