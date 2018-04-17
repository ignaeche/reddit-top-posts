package com.ignaeche.reddittopposts.di

import com.ignaeche.reddittopposts.MainActivity
import com.ignaeche.reddittopposts.ui.PostListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    // Fragments
    @ContributesAndroidInjector
    abstract fun contributePostListFragment(): PostListFragment
}