package com.air.core.di.modules

import com.air.core.BuildConfig
import com.air.core.di.qualifiers.BaseUrl
import com.air.core.network.NetworkStatusManager
import com.air.core.network.NetworkUnavailable
import com.air.core.network.services.CoinsService
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
import javax.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideJsonConverter(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://min-api.cryptocompare.com/data/"

    @Provides
    @Singleton
    fun provideCoinsService(retrofit: Retrofit): CoinsService = CoinsService(retrofit)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
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
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
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
    @Singleton
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