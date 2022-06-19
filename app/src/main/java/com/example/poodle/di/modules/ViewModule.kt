package com.example.poodle.di.modules

import com.example.poodle.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment
}