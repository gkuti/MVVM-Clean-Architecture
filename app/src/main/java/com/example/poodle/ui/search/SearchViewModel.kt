package com.example.poodle.ui.search

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.util.cancelIfActive
import com.example.domain.model.Breed
import com.example.domain.model.Result
import com.example.domain.usecase.SearchUsecase
import com.example.poodle.ui.utils.NO_CHARACTER
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchUsecase: SearchUsecase
) : ViewModel() {
    private var searchJob: Job? = null
    private val _resultState: MutableStateFlow<Result<List<Breed>>> =
        MutableStateFlow(Result.Success(emptyList()))
    val resultState = _resultState.asStateFlow()
    var scrollState: Parcelable? = null

    fun search(query: String) {
        searchJob.cancelIfActive()
        if (query == NO_CHARACTER) {
            _resultState.value = Result.Success(emptyList())
            return
        }
        searchJob = viewModelScope.launch {
            searchUsecase.search(query).collect {
                _resultState.value = it
            }
        }
    }
}
