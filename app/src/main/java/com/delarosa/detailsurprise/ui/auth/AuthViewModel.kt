package com.delarosa.detailsurprise.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delarosa.data.ResultData
import com.delarosa.detailsurprise.ui.common.Event
import com.delarosa.detailsurprise.ui.common.ScopedViewModel
import com.delarosa.usecases.SendAuthCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AuthViewModel(
    private val sendAuthCode: SendAuthCode,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private var counterMin = 0
    private val counterMax = 3

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model


    private val _navigationMessage = MutableLiveData<Event<Unit>>()
    val navigationMessage: LiveData<Event<Unit>> = _navigationMessage

    private val _navigationAdmin = MutableLiveData<Event<Unit>>()
    val navigationAdmin: LiveData<Event<Unit>> = _navigationAdmin


    fun buttonClicked(number: String) {
        launch {
            when (val result = sendAuthCode.invoke(number)) {
                is ResultData.Success -> {
                    _navigationMessage.value = Event(Unit)
                }
                is ResultData.Error -> {
                    val error = result.exception.toString()
                    _model.value = UiModel.ShowError(error)
                }
            }
        }
    }

    fun invisibleAdminClicked() {
        counterMin += 1
        if (counterMin == counterMax) {
            _navigationAdmin.value = Event(Unit)
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        class ShowError(val error: String) : UiModel()
    }


}
