package com.delarosa.detailsurprise.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delarosa.detailsurprise.ui.common.Event
import com.delarosa.detailsurprise.ui.common.ScopedViewModel
import com.delarosa.usecases.GetVideo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class VideoViewModel(
    private val getVideo: GetVideo,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> = _navigation

    private lateinit var path: String

    init {
        getVideo()
    }

    private fun getVideo() {
        launch {
            val result = getVideo.invoke()
            path = result
            _model.value = UiModel.ShowVideo(path)
        }
    }

    fun buttonClicked() {
        _navigation.value = Event(Unit)
    }

    fun replayButtonClicked() {
        _model.value = UiModel.ShowVideo(path)
    }

    fun downloadButtonClicked() {
        _model.value = UiModel.DownloadVideo(path)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        class ShowVideo(val video: String) : UiModel()
        class DownloadVideo(val video: String) : UiModel()
    }
}
