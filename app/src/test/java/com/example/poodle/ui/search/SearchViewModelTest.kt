package com.example.poodle.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.repositories.SearchRepositoryImpl
import com.example.domain.model.Breed
import com.example.domain.model.Result
import com.example.domain.usecase.SearchUsecase
import com.example.poodle.ui.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun search_success() {
        val breed = Breed((1..5).random(), "Shiba Inu")
        val list = arrayListOf(breed)
        val query = "Shiba"
        val searchRepository: SearchRepositoryImpl = mock {
            on { search(query) } doReturn flow {
                emit(Result.Success(list))
            }
        }

        val searchViewModel = SearchViewModel(SearchUsecase(searchRepository))
        searchViewModel.search(query)
        Assert.assertEquals(searchViewModel.resultState.value, Result.Success(list))
        Assert.assertEquals(searchViewModel.resultState.value.getResultOrNull()?.get(0), breed)
    }

    @Test
    fun search_failure() {
        val exception = IOException()
        val query = "Shiba"

        val searchRepository: SearchRepositoryImpl = mock {
            on { search(query) } doReturn flow {
                emit(Result.Failure(exception))
            }
        }

        val searchViewModel = SearchViewModel(SearchUsecase(searchRepository))
        searchViewModel.search(query)
        Assert.assertEquals(searchViewModel.resultState.value, Result.Failure(exception))
    }
}