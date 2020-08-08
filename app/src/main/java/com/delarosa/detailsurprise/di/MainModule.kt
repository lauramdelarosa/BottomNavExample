package com.delarosa.detailsurprise.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.delarosa.data.datasource.LocalDataSource
import com.delarosa.data.datasource.RemoteAuthDataSource
import com.delarosa.data.datasource.RemoteCompanyDataSource
import com.delarosa.data.datasource.RemoteDetailSurpriseDataSource
import com.delarosa.data.repository.AuthRepository
import com.delarosa.data.repository.CompanyRepository
import com.delarosa.data.repository.DetailSurpriseRepository
import com.delarosa.detailsurprise.R
import com.delarosa.detailsurprise.data.RetrofitBuild
import com.delarosa.detailsurprise.data.remote.endpoints.AuthService
import com.delarosa.detailsurprise.data.remote.endpoints.CompanyService
import com.delarosa.detailsurprise.data.remote.endpoints.DetailSurpriseService
import com.delarosa.detailsurprise.data.source.LocalDataSourceImpl
import com.delarosa.detailsurprise.data.source.RemoteAuthAuthDataSourceImpl
import com.delarosa.detailsurprise.data.source.RemoteCompanyDataSourceImpl
import com.delarosa.detailsurprise.data.source.RemoteDetailSurpriseDataSourceImpl
import com.delarosa.detailsurprise.ui.admin.AdminCompanyFragment
import com.delarosa.detailsurprise.ui.admin.AdminCompanyViewModel
import com.delarosa.detailsurprise.ui.admin.AdminMessageFragment
import com.delarosa.detailsurprise.ui.admin.AdminMessageViewModel
import com.delarosa.detailsurprise.ui.auth.AuthFragment
import com.delarosa.detailsurprise.ui.auth.AuthViewModel
import com.delarosa.detailsurprise.ui.info.InfoFragment
import com.delarosa.detailsurprise.ui.info.InfoViewModel
import com.delarosa.detailsurprise.ui.message.MessageFragment
import com.delarosa.detailsurprise.ui.message.MessageViewModel
import com.delarosa.detailsurprise.ui.video.VideoFragment
import com.delarosa.detailsurprise.ui.video.VideoViewModel
import com.delarosa.usecases.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
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

    scope(named<AuthFragment>()) {
        viewModel {
            AuthViewModel(sendAuthCode = get(), uiDispatcher = get())
        }
        scoped { SendAuthCode(authRepository = get()) }
    }

    scope(named<InfoFragment>()) {
        viewModel {
            InfoViewModel(getCompany = get(), uiDispatcher = get())
        }
        scoped { GetCompany(detailSurpriseRepository = get(), companyRepository = get()) }

    }

    scope(named<MessageFragment>()) {
        viewModel {
            MessageViewModel(getMessage = get(), getColor = get(), uiDispatcher = get())
        }
        scoped { GetMessage(detailSurpriseRepository = get()) }
        scoped { GetColor(detailSurpriseRepository = get()) }
    }

    scope(named<VideoFragment>()) {
        viewModel {
            VideoViewModel(getVideo = get(), uiDispatcher = get())
        }
        scoped { GetVideo(detailSurpriseRepository = get()) }
    }

    scope(named<AdminMessageFragment>()) {
        viewModel {
            AdminMessageViewModel(sendDetailSurprise = get(), uiDispatcher = get())
        }
        scoped { SendDetailSurprise(detailSurpriseRepository = get()) }
    }

    scope(named<AdminCompanyFragment>()) {
        viewModel {
            AdminCompanyViewModel(sendCompany = get(), uiDispatcher = get())
        }
        scoped { SendCompany(companyRepository = get()) }
    }
}

val dataModule = module {
    factory {
        DetailSurpriseRepository(
            localDataSource = get(),
            remoteDetailSurpriseDataSource = get()
        )
    }
    factory { AuthRepository(localDataSource = get(), remoteAuthDataSource = get()) }
    factory { CompanyRepository(remoteCompanyDataSource = get()) }
}

val appModule = module {
    single(named("baseUrl")) { androidContext().resources.getString(R.string.base_url) }
    single { RetrofitBuild(get(named("baseUrl"))) }
    single { get<RetrofitBuild>().retrofit.create(AuthService::class.java) }
    single { get<RetrofitBuild>().retrofit.create(CompanyService::class.java) }
    single { get<RetrofitBuild>().retrofit.create(DetailSurpriseService::class.java) }
    single { provideSharedPref(androidApplication()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    factory<RemoteAuthDataSource> { RemoteAuthAuthDataSourceImpl(get()) }
    factory<RemoteCompanyDataSource> { RemoteCompanyDataSourceImpl(get()) }
    factory<RemoteDetailSurpriseDataSource> { RemoteDetailSurpriseDataSourceImpl(get()) }
    factory<LocalDataSource> { LocalDataSourceImpl(get()) }
}

fun provideSharedPref(app: Application): SharedPreferences {
    return app.applicationContext.getSharedPreferences(
        "SHARED_PREFERENCE_NAME",
        Context.MODE_PRIVATE
    )
}
