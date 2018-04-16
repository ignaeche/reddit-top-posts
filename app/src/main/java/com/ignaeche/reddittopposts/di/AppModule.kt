package com.ignaeche.reddittopposts.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ignaeche.reddittopposts.api.RedditAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton @Provides
    fun providesRedditAPI(gson: Gson): RedditAPI {
        return Retrofit.Builder()
                .baseUrl(RedditAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RedditAPI::class.java)
    }
}