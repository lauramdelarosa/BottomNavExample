package com.delarosa.detailsurprise.data.remote.response

data class CompanyResponse(
    val message: String,
    val color: String,
    val whatsapp: String?,
    val instagram: String?,
    val twitter: String?,
    val facebook: String?,
    val webpage: String?
)
