package com.example.githubsearch.network
import com.example.githubsearch.model.GHRepo
import com.example.githubsearch.model.RepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("search/repositories")
    fun getRepositories(
        @Query("q") query: String = "language:swift",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc"
    ): Call<RepoResponse>
}





