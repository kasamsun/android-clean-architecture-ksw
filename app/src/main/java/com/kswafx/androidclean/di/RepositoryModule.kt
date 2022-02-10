package com.kswafx.androidclean.di

import com.kswafx.androidclean.data.remote.WemoApi
import com.kswafx.androidclean.data.remote.WemoService
import com.kswafx.androidclean.data.repository.StaffRepositoryImpl
import com.kswafx.androidclean.domain.repository.StaffRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWemoApi(retrofit: Retrofit) : WemoApi {
        return retrofit.create(WemoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWemoService(wemoApi: WemoApi) : WemoService {
        return WemoService(wemoApi)
    }

    @Provides
    @Singleton
    fun provideStaffRepository(wemoService: WemoService) : StaffRepository {
        return StaffRepositoryImpl(wemoService)
    }
}