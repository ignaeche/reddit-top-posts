package com.ignaeche.reddittopposts.di

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ignaeche.reddittopposts.api.LiveDataCallAdapterFactory
import com.ignaeche.reddittopposts.api.RedditAPI
import com.ignaeche.reddittopposts.db.AppDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {

    @Singleton @Provides
    fun providesAppDatabase(application: Application) : AppDatabase {
        return Room
                .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .build()
    }

    @Singleton @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton @Provides
    fun providesRedditAPI(gson: Gson): RedditAPI {
        return Retrofit.Builder()
                .baseUrl(RedditAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(RedditAPI::class.java)
    }
}