package com.delarosa.prueba

import com.delarosa.data.datasource.RemoteIceCreamDataSource
import com.delarosa.prueba.datasources.FakeRemoteDataSource
import com.delarosa.prueba.di.dataModule
import com.delarosa.testshared.mockedIceCream
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single<RemoteIceCreamDataSource> { FakeRemoteDataSource() }
    single { Dispatchers.Unconfined }
}

val fakeListIceCream = listOf(
    mockedIceCream.copy(name1="icecream1"),
    mockedIceCream.copy(name1="icecream2"),
    mockedIceCream.copy(name1="icecream3"),
    mockedIceCream.copy(name1="icecream4")
)


