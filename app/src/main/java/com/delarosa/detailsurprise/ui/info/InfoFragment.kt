package com.delarosa.detailsurprise.ui.info

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.delarosa.detailsurprise.R
import com.delarosa.detailsurprise.ui.common.startLink
import com.delarosa.detailsurprise.ui.message.MessageViewModel
import kotlinx.android.synthetic.main.fragment_info.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class InfoFragment : Fragment() {

    private val viewModel: InfoViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.model.observe(this, Observer(::updateUi))

        viewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()
                ?.let {
                    findNavController().navigate(R.id.action_message_to_video)
                }
        })

        instagram?.setOnClickListener {
            viewModel.redirectSocialMedia(viewModel.instagramLink)
        }
        whatsapp?.setOnClickListener {
            viewModel.redirectSocialMedia(viewModel.whatsappLink)
        }
        twitter?.setOnClickListener {
            viewModel.redirectSocialMedia(viewModel.twitterLink)
        }
        webpage?.setOnClickListener {
            viewModel.redirectSocialMedia(viewModel.webpageLink)
        }
        facebook?.setOnClickListener {
            viewModel.redirectSocialMedia(viewModel.facebookLink)
        }
    }

    private fun updateUi(model: InfoViewModel.UiModel) {
        when (model) {
            is InfoViewModel.UiModel.Redirect -> {
                activity?.let { it.startLink(model.page) }
            }
            is InfoViewModel.UiModel.ShowBackground -> {
                val anim = when (model.color) {
                    MessageViewModel.PINK -> R.raw.pink
                    MessageViewModel.BLUE -> R.raw.blue
                    MessageViewModel.MULTICOLOR -> R.raw.multicolor
                    MessageViewModel.ORANGE -> R.raw.orange
                    else -> R.raw.multicolor
                }
                backgroundView?.setAnimation(anim)
            }
            is InfoViewModel.UiModel.ShowMessage -> {
                messageText?.text = model.message
            }
            is InfoViewModel.UiModel.ShowError -> {
                //show error
            }
            is InfoViewModel.UiModel.SetVisibility -> {
                when (model.button) {
                    InfoViewModel.SocialMedia.Whatsapp -> whatsapp?.visibility = model.visibility
                    InfoViewModel.SocialMedia.Facebook -> facebook?.visibility = model.visibility
                    InfoViewModel.SocialMedia.Twitter -> twitter?.visibility = model.visibility
                    InfoViewModel.SocialMedia.Instagram -> instagram?.visibility = model.visibility
                    InfoViewModel.SocialMedia.Webpage -> webpage?.visibility = model.visibility
                }
            }
        }
    }

}
