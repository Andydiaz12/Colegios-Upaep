package com.upaep.colegios.model.base.retrofit

import com.upaep.colegios.model.base.ColegiosInterface
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
    fun provideRetrofit(myServiceInterceptor: MyServiceInterceptor): Retrofit {
        val interceptor: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(myServiceInterceptor).build()
        return Retrofit.Builder()
            .baseUrl("https://apipruebas.upaep.mx/services/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesApiClient(retrofit: Retrofit): ColegiosInterface {
        return retrofit.create(ColegiosInterface::class.java)
    }
}