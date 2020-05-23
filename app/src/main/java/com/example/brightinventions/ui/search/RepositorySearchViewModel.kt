package com.example.brightinventions.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightinventions.core.Result
import com.example.brightinventions.data.repository.EmptyOfflineDataContainerException
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.usecase.GetRepositoryUseCase
import com.example.brightinventions.domain.usecase.RepositorySearchCriteria
import com.example.brightinventions.utills.SearchQueryExtractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositorySearchViewModel @Inject constructor(
    private val getRepositoryUseCase: GetRepositoryUseCase,
    private val searchQueryExtractor: SearchQueryExtractor<RepositorySearchCriteria>
) : ViewModel() {

    private val _repositoryDataState: MutableLiveData<RepositoryDataState> = MutableLiveData()
    val containerDataState: LiveData<RepositoryDataState>
        get() = _repositoryDataState

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val query = MutableLiveData<String>()

    fun search() {
        query.value?.let { query ->
            viewModelScope.launch {
                when (val result = getRepositoryUseCase(searchQueryExtractor.extract(query))) {
                    is Result.Success -> handleSuccess(result.data)
                    is Result.Error -> handleError(result.exception)
                }
            }
        }
    }

    private fun handleError(exception: Exception) {
        if (exception is EmptyOfflineDataContainerException) {
            _repositoryDataState.value = RepositoryDataState.Empty
        } else {
            handleException(exception)
        }
    }

    private fun handleException(exception: Exception) {
        exception.message?.let {
            _errorMessage.value = it
        }
    }

    private fun handleSuccess(data: Repository) {
        _repositoryDataState.value = RepositoryDataState.HasData(data)
    }

}

sealed class RepositoryDataState {
    object Empty : RepositoryDataState()
    data class HasData(val data: Repository) : RepositoryDataState()
}