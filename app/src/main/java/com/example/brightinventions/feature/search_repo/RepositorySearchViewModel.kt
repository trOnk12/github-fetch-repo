package com.example.brightinventions.feature.search_repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightinventions.core.Result
import com.example.brightinventions.data.repository.EmptyOfflineDataContainerException
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.usecase.GetRepositoryUseCase
import com.example.brightinventions.domain.usecase.RepositorySearchCriteria
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositorySearchViewModel @Inject constructor(
    private val getRepositoryUseCase: GetRepositoryUseCase,
    private val searchQueryExtractor: SearchQueryExtractor<RepositorySearchCriteria>
) : ViewModel() {

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _containerDataState: MutableLiveData<ContainerDataState> = MutableLiveData()
    val containerDataState: LiveData<ContainerDataState>
        get() = _containerDataState

    fun search(query: String) {
        viewModelScope.launch {
            when (val result = getRepositoryUseCase(searchQueryExtractor.extract(query))) {
                is Result.Success -> handleSuccess(result.data)
                is Result.Error -> handleError(result.exception)
            }
        }
    }

    private fun handleSuccess(data: Repository) {
        _containerDataState.value = ContainerDataState.HasData(data)
    }

    private fun handleError(exception: Exception) {
        if (exception is EmptyOfflineDataContainerException) {
            _containerDataState.value = ContainerDataState.Empty
        }
        exception.message?.let {
            _errorMessage.value = it
        }
    }

}

sealed class ContainerDataState {
    object Empty : ContainerDataState()
    data class HasData(val data: Repository) : ContainerDataState()
}