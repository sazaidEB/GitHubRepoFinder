package com.example.githubsearch.database
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubsearch.model.GHRepo

@Dao
interface GHRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<GHRepo>)

    @Query("SELECT * FROM GHRepo WHERE name LIKE '%' || :query || '%' OR id LIKE '%' || :query || '%'")
    fun searchRepos(query: String): LiveData<List<GHRepo>>

    @Query("SELECT * FROM GHRepo")
    fun getAllRepos(): LiveData<List<GHRepo>>
}

@Database(entities = [GHRepo::class], version = 1)
abstract class GHRepoDatabase : RoomDatabase() {
    abstract fun ghRepoDao(): GHRepoDao
}
