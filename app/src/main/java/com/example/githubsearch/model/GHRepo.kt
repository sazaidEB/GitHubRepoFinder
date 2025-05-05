package com.example.githubsearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class GHRepo(
    @PrimaryKey val id: Int,
    val name: String,
    val repoURL: String
) : Serializable
