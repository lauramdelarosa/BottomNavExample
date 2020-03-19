package com.delarosa.prueba.ui.uientities


import com.delarosa.domain.IceCream
import java.io.Serializable

data class Purchase(val orderList: List<IceCream>, val total: Float) : Serializable