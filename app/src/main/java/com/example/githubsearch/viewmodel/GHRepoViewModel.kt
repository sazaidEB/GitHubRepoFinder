package com.example.githubsearch.viewmodel

import androidx.lifecycle.*
import com.example.githubsearch.model.GHRepo
import com.example.githubsearch.repository.GHRepoRepository
import com.example.githubsearch.utils.Resource
import kotlinx.coroutines.launch

class GHRepoViewModel(private val repository: GHRepoRepository) : ViewModel() {

    val allRepos: LiveData<List<GHRepo>> = repository.allRepos
    private val _status = MutableLiveData<Resource<Unit>>()
    val status: LiveData<Resource<Unit>> = _status

    fun fetchRepos() {
        viewModelScope.launch {
            _status.value = repository.fetchReposFromApi()
        }
    }

    fun searchRepos(query: String): LiveData<List<GHRepo>> {
        return repository.searchRepos(query)
    }
}
