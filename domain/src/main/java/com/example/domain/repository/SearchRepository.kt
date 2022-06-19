package com.example.domain.repository

import com.example.domain.model.Breed
import com.example.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<Result<List<Breed>>>
}