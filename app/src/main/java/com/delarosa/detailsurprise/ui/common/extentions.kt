package com.delarosa.detailsurprise.ui.common

import android.app.Activity
import android.content.Intent
import android.net.Uri

fun Activity.startLink(link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    intent.apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
    startActivity(intent)
}
