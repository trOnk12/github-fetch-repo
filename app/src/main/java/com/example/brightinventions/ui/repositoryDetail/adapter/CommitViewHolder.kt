package com.example.brightinventions.ui.repositoryDetail.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.brightinventions.databinding.CommitItemBinding
import com.example.brightinventions.ui.model.Commit

class CommitViewHolder(private val commitItemBinding: CommitItemBinding) :
    RecyclerView.ViewHolder(commitItemBinding.root) {

    fun bind(commit: Commit, onItemClick: (commit: Commit, isChecked: Boolean) -> Unit) {
        commitItemBinding.commit = commit
        commitItemBinding.shouldSendCb.setOnCheckedChangeListener { _, isChecked ->
            onItemClick(
                commit,
                isChecked
            )
        }
        commitItemBinding.executePendingBindings()
    }

}
