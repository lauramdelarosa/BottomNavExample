package com.delarosa.detailsurprise.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.delarosa.detailsurprise.fakeListIceCream
import com.delarosa.detailsurprise.initMockedDi
import com.delarosa.detailsurprise.ui.icecream.IceCreamViewModel
import com.delarosa.usecases.GetIceCream
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IceCreamIntegrationTest : AutoCloseKoinTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerModel: Observer<IceCreamViewModel.UiModel>

    private lateinit var vm: IceCreamViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { IceCreamViewModel(get(), get()) }
            factory { GetIceCream(get()) }
        }
        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `ice cream data is loaded from server`() {
        vm.model.observeForever(observerModel)
        verify(observerModel).onChanged(IceCreamViewModel.UiModel.Content(fakeListIceCream))
    }
}
