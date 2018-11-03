package com.mvvm.tutorials.syleiman.mvvmtutorials.ui.post

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mvvm.tutorials.syleiman.mvvmtutorials.R
import com.mvvm.tutorials.syleiman.mvvmtutorials.databinding.ItemPostBinding
import com.mvvm.tutorials.syleiman.mvvmtutorials.model.Post

class PostListAdapter: RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private lateinit var postList:List<Post>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapter.ViewHolder {
        return ViewHolder(      // Use DataBindingUtil instead of LayoutInflater
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostListAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return if(::postList.isInitialized) postList.size else 0        // !!!
    }

    fun updatePostList(postList:List<Post>){
        this.postList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = PostViewModel()

        fun bind(post:Post){
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}