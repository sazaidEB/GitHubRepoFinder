package com.example.githubsearch.model

data class RepoItem(
    val id: Int,
    val name: String,
    val html_url: String
) {
    fun toGHRepo() = GHRepo(id, name, html_url)
}
