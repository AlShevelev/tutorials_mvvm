package com.mvvm.tutorials.syleiman.mvvmtutorials.ui.post

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import com.mvvm.tutorials.syleiman.mvvmtutorials.R
import com.mvvm.tutorials.syleiman.mvvmtutorials.base.BaseViewModel
import com.mvvm.tutorials.syleiman.mvvmtutorials.model.Post
import com.mvvm.tutorials.syleiman.mvvmtutorials.model.PostDao
import com.mvvm.tutorials.syleiman.mvvmtutorials.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * ViewModel for [PostListActivity]
 */
class PostListViewModel(private val postDao: PostDao) : BaseViewModel(){
    private lateinit var subscription: Disposable

    // Loading indicator visibility
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    // ResId or error message
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    // Click listener for Reload button
    val errorClickListener = View.OnClickListener { loadPosts() }

    // List adapter
    val postListAdapter: PostListAdapter = PostListAdapter()

    @Inject
    lateinit var postApi: PostApi

    init {
        Log.d("VIEW_MODEL", "init")
    }

    init{
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadPosts(){
        subscription = Observable.fromCallable { postDao.all }
            .concatMap {
                dbPostList ->

                Thread.sleep(5000)

                if(dbPostList.isEmpty())
                    postApi.getPosts().concatMap {
                        apiPostList -> postDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onRetrievePostListStart()
                onRetrievePostListSuccess(listOf())

            }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { onRetrievePostListError() }
            )
    }

    private fun onRetrievePostListStart(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrievePostListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: List<Post>) {
        errorMessage.value = null

        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(){
        errorMessage.value = R.string.post_error
    }
}