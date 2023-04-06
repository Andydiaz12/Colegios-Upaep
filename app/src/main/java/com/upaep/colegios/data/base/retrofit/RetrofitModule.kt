package com.upaep.colegios.data.base.retrofit

import com.upaep.colegios.data.base.ColegiosInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("https://mocki.io/v1/")
            .baseUrl("https://api.upaep.mx/services/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiClient(retrofit: Retrofit): ColegiosInterface {
        return retrofit.create(ColegiosInterface::class.java)
    }
}