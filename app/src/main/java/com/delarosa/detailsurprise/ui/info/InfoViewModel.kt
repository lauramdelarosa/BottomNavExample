package com.delarosa.detailsurprise.ui.info

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.delarosa.data.ResultData
import com.delarosa.detailsurprise.ui.common.Event
import com.delarosa.detailsurprise.ui.common.ScopedViewModel
import com.delarosa.domain.Company
import com.delarosa.usecases.GetCompany
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class InfoViewModel(
    private val getCompany: GetCompany,
    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> = _navigation

    var whatsappLink = "whatsapp"
    var instagramLink = "instagram"
    var facebookLink = "facebook"
    var twitterLink = "twitter"
    var webpageLink = "webpage"

    init {
        getInfoCompany()
    }

    private fun getInfoCompany() {
        launch {
            when (val result = getCompany.invoke()) {
                is ResultData.Success -> {
                    if (result.data.color != "none") {
                        _model.value = UiModel.ShowBackground(result.data.color)
                    }
                    _model.value = UiModel.ShowMessage(result.data.message)
                    evaluateLinks(result.data)
                }
                is ResultData.Error -> {
                    val error = result.exception.toString()
                    _model.value = UiModel.ShowError(error)
                }
            }
        }
    }

    fun redirectSocialMedia(id: String) {
        _model.value = UiModel.Redirect(id)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    private fun evaluateLinks(data: Company) {
        if (data.facebook.isNullOrEmpty()) {
            _model.value = UiModel.SetVisibility(View.GONE, SocialMedia.Facebook)
        } else {
            facebookLink = data.facebook!!
        }

        if (data.instagram.isNullOrEmpty()) {
            _model.value = UiModel.SetVisibility(View.GONE, SocialMedia.Instagram)
        } else {
            instagramLink = data.instagram!!
        }

        if (data.twitter.isNullOrEmpty()) {
            _model.value = UiModel.SetVisibility(View.GONE, SocialMedia.Twitter)
        } else {
            twitterLink = data.twitter!!
        }

        if (data.webpage.isNullOrEmpty()) {
            _model.value = UiModel.SetVisibility(View.GONE, SocialMedia.Webpage)
        } else {
            webpageLink = data.webpage!!
        }

        if (data.whatsapp.isNullOrEmpty()) {
            _model.value = UiModel.SetVisibility(View.GONE, SocialMedia.Whatsapp)
        } else {
            whatsappLink = data.whatsapp!!
        }

    }

    sealed class UiModel {
        class Redirect(val page: String) : UiModel()
        class ShowMessage(val message: String) : UiModel()
        class ShowError(val error: String) : UiModel()
        class ShowBackground(val color: String) : UiModel()
        class SetVisibility(val visibility: Int, val button: SocialMedia) : UiModel()

    }

    enum class SocialMedia {
        Facebook, Instagram, Whatsapp, Webpage, Twitter
    }
}
