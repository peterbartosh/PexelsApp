package com.example.pexelsapp.di

import android.content.Context
import androidx.room.Room
import com.example.pexelsapp.data.local.LocalDao
import com.example.pexelsapp.data.local.LocalDatabase
import com.example.pexelsapp.data.remote.AuthorizationInterceptor
import com.example.pexelsapp.data.remote.CachingInterceptor
import com.example.pexelsapp.data.remote.Callbacks
import com.example.pexelsapp.data.remote.PexelsApi
import com.example.pexelsapp.data.repository.RoomRepository
import com.example.pexelsapp.data.utils.BASE_URL
import com.example.pexelsapp.data.utils.CACHE_MAX_SIZE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // remote
    @Provides @Singleton
    fun provideCallbacks(@ApplicationContext context: Context) = Callbacks(context)

    @Provides @Singleton
    fun provideAuthorizationInterceptor() = AuthorizationInterceptor()

    @Provides @Singleton
    fun provideCachingInterceptor(@ApplicationContext context: Context) = CachingInterceptor(context)

    @Provides @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context,
        authorizationInterceptor: AuthorizationInterceptor,
        cachingInterceptor: CachingInterceptor
    ) = OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, CACHE_MAX_SIZE))
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(cachingInterceptor)
            .build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(PexelsApi::class.java)

    // local
    @Provides @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides @Singleton
    fun provideLocalDao(localDatabase: LocalDatabase) = localDatabase.getDao()

    @Provides @Singleton
    fun provideRoomRepository(localDao: LocalDao) = RoomRepository(localDao)
}