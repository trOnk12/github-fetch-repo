package com.example.brightinventions.ui.repositorySearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brightinventions.core.functional.Result
import com.example.brightinventions.core.functional.SingleLiveData
import com.example.brightinventions.data.source.local.EmptyCacheException
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.usecase.GetRepositoryUseCase
import com.example.brightinventions.domain.usecase.RepositorySearchCriteria
import com.example.brightinventions.utils.RepositorySearchQueryExtractor
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class RepositorySearchViewModel @Inject constructor(
    private val getRepositoryUseCase: GetRepositoryUseCase,
    private val repositorySearchQueryExtractor: RepositorySearchQueryExtractor
) : ViewModel() {

    private val _repositoryDataState: SingleLiveData<RepositoryDataState> = SingleLiveData()
    val repository: LiveData<RepositoryDataState>
        get() = _repositoryDataState

    private val _errorMessage: SingleLiveData<String> = SingleLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val query = MutableLiveData<String>()

    fun search() {
        query.value?.let {
            getRepository(
                repositorySearchQueryExtractor.extract(it)
            )
        }
    }

    private fun getRepository(repositorySearchCriteria: RepositorySearchCriteria) {
        viewModelScope.launch {
            when (val result = getRepositoryUseCase(repositorySearchCriteria)) {
                is Result.Success -> handleRepository(result.data)
                is Result.Error -> handleError(result.exception)
            }
        }
    }

    private fun handleRepository(repository: Repository) {
        _repositoryDataState.value = RepositoryDataState.Data(repository)
    }

    private fun handleError(exception: Exception) {
        when (exception) {
            is EmptyCacheException -> handleEmptyCacheException()
            is HttpException -> handleHttpException(exception)
            else -> handleException(exception)
        }
    }

    private fun handleEmptyCacheException() {
        _repositoryDataState.value = RepositoryDataState.EmptyData
    }

    private fun handleHttpException(exception: HttpException) {
        if (exception.code() == 404) {
            _repositoryDataState.value = RepositoryDataState.EmptyData
        }
    }

    private fun handleException(exception: Exception) {
        exception.message?.let {
            _errorMessage.value = it
        }
    }

}

sealed class RepositoryDataState {
    object EmptyData : RepositoryDataState()
    data class Data(val data: Repository) : RepositoryDataState()
}