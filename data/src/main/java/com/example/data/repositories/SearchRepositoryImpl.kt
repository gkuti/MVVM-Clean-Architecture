package com.example.data.repositories

import com.example.data.remote.SearchApi
import com.example.data.util.apiCall
import com.example.data.util.applyCommonSideEffects
import com.example.domain.model.Breed
import com.example.domain.model.Result
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override fun search(query: String): Flow<Result<List<Breed>>> = flow {
        val result = withContext(Dispatchers.IO) {
            apiCall {
                searchApi.searchBreeds(query)
            }
        }
        emit(result)
    }.applyCommonSideEffects().catch { emit(Result.Failure(exception = it)) }
}