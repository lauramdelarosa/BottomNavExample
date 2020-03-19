package com.delarosa.data

import com.delarosa.data.datasource.RemoteIceCreamDataSource
import com.delarosa.data.repository.IceCreamRepository
import com.delarosa.testshared.mockedIceCream
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IceCreamRepositoryTest {

    @Mock
    lateinit var remoteIceCreamDataSource: RemoteIceCreamDataSource
    lateinit var iceCreamRepository: IceCreamRepository

    @Before
    fun setUp() {
        iceCreamRepository = IceCreamRepository(remoteIceCreamDataSource)
    }

    @Test
    fun `remote iceCream calls remoteIceCreamsDataSource `() {
        runBlocking {
            val remoteIceCream = listOf(mockedIceCream.copy())
            whenever(remoteIceCreamDataSource.getAllIceCream()).thenReturn(
                ResultData.Success(remoteIceCream)
            )
            when (val result = iceCreamRepository.getIceCreams()) {
                is ResultData.Success -> assertEquals(remoteIceCream, result.data)
            }
        }
    }

}