package com.example.movieapp.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.model.detail.MovieDetailsEntity
import com.example.movieapp.util.Constants.EMPTY
import com.example.movieapp.util.Constants.ID
import com.example.movieapp.util.Resource
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
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _moviesState: MutableStateFlow<Resource<MovieDetailsEntity>> =
        MutableStateFlow(value = Resource.Loading())
    val moviesState: MutableStateFlow<Resource<MovieDetailsEntity>> get() = _moviesState

    init {
        viewModelScope.launch { getMovieDetail() }
    }

    suspend fun getMovieDetail() {
        val id = savedStateHandle.getStateFlow(ID, EMPTY)
        repository.getMovieDetails(id.value)
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Resource.Loading()
            )
            .collect {
                _moviesState.value = it
            }
    }

    fun setId(id: String) {
        savedStateHandle[ID] = id
    }
}
