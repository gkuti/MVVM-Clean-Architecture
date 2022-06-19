package com.example.poodle.di.modules

import com.example.data.remote.SearchApi
import com.example.data.repositories.SearchRepositoryImpl
import com.example.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesSearchRepository(
        userApi: SearchApi
    ): SearchRepository =
        SearchRepositoryImpl(userApi)
}
