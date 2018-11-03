package com.mvvm.tutorials.syleiman.mvvmtutorials.ui.post

import android.arch.lifecycle.MutableLiveData
import com.mvvm.tutorials.syleiman.mvvmtutorials.base.BaseViewModel
import com.mvvm.tutorials.syleiman.mvvmtutorials.model.Post

/** ViewModel for every [Post] */
class PostViewModel: BaseViewModel() {

    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(post: Post){
        postTitle.value = post.title
        postBody.value = post.body
    }

    fun getPostTitle():MutableLiveData<String>{
        return postTitle
    }

    fun getPostBody():MutableLiveData<String>{
        return postBody
    }
}