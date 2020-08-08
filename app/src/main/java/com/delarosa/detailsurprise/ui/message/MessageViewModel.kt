package com.delarosa.detailsurprise.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delarosa.detailsurprise.ui.common.Event
import com.delarosa.detailsurprise.ui.common.ScopedViewModel
import com.delarosa.usecases.GetColor
import com.delarosa.usecases.GetMessage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessageViewModel(
    private val getMessage: GetMessage,
    private val getColor: GetColor,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> = _navigation

    init {
        getColorBackground()
        getMessageText()
    }

    private fun getMessageText() {
        launch {
            val result = getMessage.invoke()
                _model.value = UiModel.ShowMessage(result)
        }
    }

    private fun getColorBackground() {
        launch {
             val result = getColor.invoke()
                _model.value = UiModel.ShowBackground(result)
        }
    }

    fun buttonClicked() {
        _navigation.value = Event(Unit)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        class ShowMessage(val message: String) : UiModel()
        class ShowBackground(val background: String) : UiModel()
    }

    companion object {
        const val PINK = "pink"
        const val MULTICOLOR = "multicolor"
        const val BLUE = "blue"
        const val ORANGE = "orange"
    }
}
