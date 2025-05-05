package com.example.githubsearch.repository

import androidx.lifecycle.LiveData
import com.example.githubsearch.database.GHRepoDao
import com.example.githubsearch.model.GHRepo
import com.example.githubsearch.network.RetrofitInstance
import com.example.githubsearch.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GHRepoRepository(private val dao: GHRepoDao) {

    val allRepos: LiveData<List<GHRepo>> = dao.getAllRepos()

    suspend fun fetchReposFromApi(): Resource<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getRepositories().execute()
                if (response.isSuccessful) {
                    val repoList = response.body()?.items?.map { it.toGHRepo() } ?: emptyList()
                    dao.insertAll(repoList)
                    Resource.Success(Unit)
                } else {
                    Resource.Error("API Error")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun searchRepos(query: String): LiveData<List<GHRepo>> {
        return dao.searchRepos(query)
    }
}
