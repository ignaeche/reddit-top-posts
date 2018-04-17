package com.ignaeche.reddittopposts.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.ignaeche.reddittopposts.model.PostData
import com.ignaeche.reddittopposts.repository.PostsRepository
import com.ignaeche.reddittopposts.repository.Resource
import javax.inject.Inject

class PostsViewModel
    @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel() {

    fun getPosts(): LiveData<Resource<List<PostData>>> {
        return postsRepository.getPosts()
    }
}