package com.example.movieapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.model.list.MovieListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _moviesState: MutableStateFlow<PagingData<MovieListEntity>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesState: MutableStateFlow<PagingData<MovieListEntity>> get() = _moviesState

    init {
        viewModelScope.launch { getMovies() }
    }

    private suspend fun getMovies() {
        repository.getAllMovies()
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PagingData.empty()
            )
            .collect {
                _moviesState.value = it
            }
    }
}
