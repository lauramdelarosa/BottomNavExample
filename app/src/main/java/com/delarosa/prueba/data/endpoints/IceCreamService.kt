package com.delarosa.prueba.data.endpoints

import com.delarosa.domain.IceCream
import retrofit2.Response
import retrofit2.http.GET

interface IceCreamService {
    @GET("products/")
    suspend fun getIceCreams(): Response<List<IceCream>>

}