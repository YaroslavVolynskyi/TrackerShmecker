package com.example.trackershmecker.di

import com.example.trackershmecker.data.remote.FirebaseApiService
import com.example.trackershmecker.data.remote.FirebaseRemoteDataSource
import com.example.trackershmecker.data.remote.RemoteDataSource
import com.example.trackershmecker.data.repository.FirebaseSyncRepository
import com.example.trackershmecker.data.repository.SyncRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://trackershmecker-default-rtdb.firebaseio.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseApiService(retrofit: Retrofit): FirebaseApiService {
        return retrofit.create(FirebaseApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(impl: FirebaseRemoteDataSource): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSyncRepository(impl: FirebaseSyncRepository): SyncRepository
}
