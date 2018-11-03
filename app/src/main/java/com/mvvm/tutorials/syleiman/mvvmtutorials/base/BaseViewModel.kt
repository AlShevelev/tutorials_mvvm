package com.mvvm.tutorials.syleiman.mvvmtutorials.base

import android.arch.lifecycle.ViewModel
import com.mvvm.tutorials.syleiman.mvvmtutorials.injection.component.DaggerViewModelInjector
import com.mvvm.tutorials.syleiman.mvvmtutorials.injection.component.ViewModelInjector
import com.mvvm.tutorials.syleiman.mvvmtutorials.injection.module.NetworkModule
import com.mvvm.tutorials.syleiman.mvvmtutorials.ui.post.PostListViewModel

abstract class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
        }
    }
}