package com.air.crypto.di

import com.air.crypto.BuildConfig
import com.air.crypto.data_source.remote.network.ApiService
import com.air.crypto.data_source.remote.network.NetworkStatusManager
import com.air.crypto.util.NetworkUnavailable
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://min-api.cryptocompare.com/data/"

    @Provides
    @ApplicationScope
    fun provideApiService(retrofit: Retrofit): ApiService = ApiService(retrofit)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @ApplicationScope
    fun provideRetrofit(
        client: OkHttpClient,
        json: Json,
        @BaseUrl baseUrl: String
    ): Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @ApplicationScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @ApplicationScope
    fun provideConnectionStatusInterceptor(
        networkStatusManager: NetworkStatusManager
    ) = Interceptor { chain ->
        if (networkStatusManager.isConnected) {
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailable()
        }
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        connectionStatusInterceptor: Interceptor
    ) = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(connectionStatusInterceptor)
            .build()
}