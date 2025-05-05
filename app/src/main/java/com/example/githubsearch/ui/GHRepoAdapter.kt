package com.example.githubsearch.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.model.GHRepo

class GHRepoAdapter(private var repoList: List<GHRepo>) :
    RecyclerView.Adapter<GHRepoAdapter.GHRepoViewHolder>() {

    inner class GHRepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoId: TextView = itemView.findViewById(R.id.repoId)
        val repoName: TextView = itemView.findViewById(R.id.repoName)

        init {
            itemView.setOnClickListener {
                val repo = repoList[adapterPosition]
                val context = itemView.context
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("url", repo.repoURL)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GHRepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return GHRepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GHRepoViewHolder, position: Int) {
        val repo = repoList[position]
        holder.repoId.text = "ID: ${repo.id}"
        holder.repoName.text = "Name: ${repo.name}"
    }

    override fun getItemCount(): Int = repoList.size

    fun updateList(newList: List<GHRepo>) {
        repoList = newList
        notifyDataSetChanged()
    }
}
