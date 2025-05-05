package com.example.githubsearch.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.githubsearch.R
import com.example.githubsearch.database.GHRepoDatabase
import com.example.githubsearch.repository.GHRepoRepository
import com.example.githubsearch.viewmodel.GHRepoViewModel
import com.example.githubsearch.viewmodel.GHRepoViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: GHRepoAdapter
    private lateinit var viewModel: GHRepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(applicationContext, GHRepoDatabase::class.java, "ghrepo-db").build()
        val repository = GHRepoRepository(db.ghRepoDao())
        val factory = GHRepoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[GHRepoViewModel::class.java]

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val searchBox: EditText = findViewById(R.id.searchBox)

        adapter = GHRepoAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.allRepos.observe(this) { list ->
            adapter.updateList(list)
        }

        viewModel.status.observe(this) { status ->
            if (status is com.example.githubsearch.utils.Resource.Error) {
                Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.fetchRepos()

        searchBox.addTextChangedListener {
            val query = it.toString()
            viewModel.searchRepos(query).observe(this) { list ->
                adapter.updateList(list)
            }
        }
    }
}
