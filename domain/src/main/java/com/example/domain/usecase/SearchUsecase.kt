package com.example.domain.usecase

import com.example.domain.model.Breed
import com.example.domain.util.Result
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchUsecase @Inject constructor(private val searchRepository: SearchRepository) {

    fun search(query: String): Flow<Result<List<Breed>>> = searchRepository.search(query)

}