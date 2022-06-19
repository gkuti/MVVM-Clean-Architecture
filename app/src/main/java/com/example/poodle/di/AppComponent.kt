package com.example.poodle.di

import com.example.poodle.application.PoodleApplication
import com.example.poodle.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ApiServiceModule::class, ViewModule::class, ViewModelModule::class, RepositoryModule::class]
)

interface AppComponent : AndroidInjector<PoodleApplication> {
    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: PoodleApplication): Builder
    }
}
   