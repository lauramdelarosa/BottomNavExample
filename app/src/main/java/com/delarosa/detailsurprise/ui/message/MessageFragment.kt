package com.delarosa.detailsurprise.ui.message

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.delarosa.detailsurprise.R
import com.delarosa.detailsurprise.ui.message.MessageViewModel.Companion.BLUE
import com.delarosa.detailsurprise.ui.message.MessageViewModel.Companion.MULTICOLOR
import com.delarosa.detailsurprise.ui.message.MessageViewModel.Companion.ORANGE
import com.delarosa.detailsurprise.ui.message.MessageViewModel.Companion.PINK
import kotlinx.android.synthetic.main.fragment_message.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class MessageFragment : Fragment() {

    private val viewModel: MessageViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return inflater.inflate(R.layout.fragment_message, container, false)
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

        next?.setOnClickListener {
            viewModel.buttonClicked()
        }
    }

    private fun updateUi(model: MessageViewModel.UiModel) {
        when (model) {
            is MessageViewModel.UiModel.ShowMessage -> {
                messageText?.text = model.message
            }
            is MessageViewModel.UiModel.ShowBackground -> {
                val anim = when (model.background) {
                    PINK -> R.raw.pink
                    BLUE -> R.raw.blue
                    MULTICOLOR -> R.raw.multicolor
                    ORANGE -> R.raw.orange
                    else -> R.raw.multicolor
                }
                backgroundView?.setAnimation(anim)
            }
        }
    }

}
