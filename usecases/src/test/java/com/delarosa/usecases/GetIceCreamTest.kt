package com.delarosa.usecases

import com.delarosa.data.ResultData
import com.delarosa.testshared.mockedIceCream
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIceCreamTest {

    @Mock
    lateinit var iceCreamRepository: IceCreamRepository

    lateinit var getIceCream: GetIceCream


    @Before
    fun setUp() {
        getIceCream = GetIceCream(iceCreamRepository)
    }

    @Test
    fun `invoke calls ice cream repository`() {
        runBlocking {
            val iceCream = listOf(mockedIceCream.copy())
            whenever(iceCreamRepository.getIceCreams()).thenReturn(ResultData.Success(iceCream))
            when (val result = getIceCream.invoke()) {
                is ResultData.Success -> {
                    Assert.assertEquals(iceCream, result.data)
                }
            }
        }
    }
}
