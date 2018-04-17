package com.ignaeche.reddittopposts.di

import android.app.Application
import com.ignaeche.reddittopposts.RedditApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class
))
interface AppComponent {
    @Component.Builder interface Builder {
        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : AppComponent
    }
    fun inject(redditApp: RedditApp)
}