package com.example.brightinventions.ui.repositoryDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brightinventions.databinding.CommitItemBinding
import com.example.brightinventions.ui.BindableAdapter
import com.example.brightinventions.ui.model.Commit

class CommitsAdapter
    : RecyclerView.Adapter<CommitViewHolder>(), BindableAdapter<List<Commit>> {

    val commitsToShare: ArrayList<Commit> = ArrayList()

    private lateinit var commits: List<Commit>

    override fun setData(data: List<Commit>) {
        commits = data
    }

    override fun getItemCount() = commits.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val commitItemBinding = CommitItemBinding.inflate(layoutInflater, parent, false);
        return CommitViewHolder(commitItemBinding)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        holder.bind(commits[position]) { commit, isChecked ->
            if (isChecked) commitsToShare.add(
                commit
            ) else commitsToShare.remove(commit)
        }
    }

}

