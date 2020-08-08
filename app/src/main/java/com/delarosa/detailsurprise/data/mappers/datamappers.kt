package com.delarosa.detailsurprise.data.mappers

import com.delarosa.domain.Company
import com.delarosa.domain.DetailSurprise
import com.delarosa.detailsurprise.data.remote.response.CompanyResponse as ServerCompany
import com.delarosa.detailsurprise.data.remote.response.DetailSurpriseRemote as ServerDetail

fun ServerDetail.toDomainDetail(): DetailSurprise =
    DetailSurprise(
        code = code,
        video = video,
        message = message,
        color = color,
        companyId = companyId
    )


fun ServerCompany.toDomainCompany(): Company =
    Company(
        message = message,
        color = color,
        facebook = facebook,
        instagram = instagram,
        webpage = webpage,
        twitter = twitter,
        whatsapp = whatsapp
    )


