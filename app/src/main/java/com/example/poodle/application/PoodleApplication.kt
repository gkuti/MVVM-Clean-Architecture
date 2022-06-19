package com.example.poodle.application

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import com.example.poodle.di.DaggerAppComponent
import javax.inject.Inject

class PoodleApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this)
            .build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}