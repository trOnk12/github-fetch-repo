package com.example.brightinventions.ui.repositorySearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightinventions.core.Result
import com.example.brightinventions.data.repository.EmptyOfflineDataContainerException
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.usecase.GetRepositoryUseCase
import com.example.brightinventions.utils.RepositorySearchQueryExtractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositorySearchViewModel @Inject constructor(
    private val getRepositoryUseCase: GetRepositoryUseCase,
    private val repositorySearchQueryExtractor: RepositorySearchQueryExtractor
) : ViewModel() {

    private val _repositoryDataState: MutableLiveData<RepositoryDataState> = MutableLiveData()
    val repositoryDataState: LiveData<RepositoryDataState>
        get() = _repositoryDataState

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val query = MutableLiveData<String>()

    fun search() {
        query.value?.let { query ->
            viewModelScope.launch {
                when (val result =
                    getRepositoryUseCase(repositorySearchQueryExtractor.extract(query))) {
                    is Result.Success -> _repositoryDataState.value =
                        RepositoryDataState.HasData(result.data)
                    is Result.Error -> handleError(result.exception)
                }
            }
        }
    }

    private fun handleError(exception: Exception) {
        if (exception is EmptyOfflineDataContainerException) {
            _repositoryDataState.value = RepositoryDataState.NoData
        } else {
            handleException(exception)
        }
    }

    private fun handleException(exception: Exception) {
        exception.message?.let {
            _errorMessage.value = it
        }
    }

}

sealed class RepositoryDataState {
    object NoData : RepositoryDataState()
    data class HasData(val data: Repository) : RepositoryDataState()
}