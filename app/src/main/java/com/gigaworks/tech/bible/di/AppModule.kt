package com.gigaworks.tech.bible.di

import android.content.Context
import androidx.room.Room
import com.gigaworks.tech.bible.BuildConfig.DEBUG
import com.gigaworks.tech.bible.cache.Database
import com.gigaworks.tech.bible.cache.dao.BookDao
import com.gigaworks.tech.bible.cache.dao.SoundDao
import com.gigaworks.tech.bible.network.service.AppService
import com.gigaworks.tech.bible.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideAppService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) : Database {
        return Room.databaseBuilder(context, Database::class.java, "pilgrims-manna-db").build()
    }

    @Provides
    fun provideBookDao(database: Database) : BookDao {
        return database.bookDao()
    }

    @Provides
    fun provideSoundDao(database: Database) : SoundDao {
        return database.soundDao()
    }

}