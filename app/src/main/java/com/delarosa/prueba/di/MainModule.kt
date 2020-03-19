package com.delarosa.prueba.di

import android.app.Application
import com.delarosa.data.datasource.RemoteIceCreamDataSource
import com.delarosa.data.repository.IceCreamRepository
import com.delarosa.prueba.R
import com.delarosa.prueba.data.RetrofitBuild
import com.delarosa.prueba.data.endpoints.IceCreamService
import com.delarosa.prueba.data.source.RemoteIceCreamDataSourceImpl
import com.delarosa.prueba.ui.icecream.IceCreamFragment
import com.delarosa.prueba.ui.icecream.IceCreamViewModel
import com.delarosa.usecases.GetIceCream
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(presentationModule, dataModule, appModule))
    }
}

val presentationModule = module {
    scope(named<IceCreamFragment>()) {
        viewModel {
            IceCreamViewModel(getIceCream = get(), uiDispatcher = get())
        }
        scoped { GetIceCream(iceCreamRepository = get()) }
    }
}

val dataModule = module {
    factory { IceCreamRepository(iceCreamDataSource = get()) }
}

val appModule = module {
    single(named("baseUrl")) { androidContext().resources.getString(R.string.base_url) }
    single { RetrofitBuild(get(named("baseUrl"))) }
    single { get<RetrofitBuild>().retrofit.create(IceCreamService::class.java) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    factory<RemoteIceCreamDataSource> { RemoteIceCreamDataSourceImpl(get()) }
}