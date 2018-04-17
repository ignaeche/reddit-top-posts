package com.ignaeche.reddittopposts.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ignaeche.reddittopposts.model.PostData
import com.ignaeche.reddittopposts.repository.PostsRepository
import com.ignaeche.reddittopposts.repository.Resource
import javax.inject.Inject

class PostsViewModel
    @Inject constructor(private val postsRepository: PostsRepository)
    : ViewModel() {

    var postId : MutableLiveData<String> = MutableLiveData()

    fun getPosts(): LiveData<Resource<List<PostData>>> {
        return postsRepository.getPosts()
    }

    fun getPost() : LiveData<PostData> {
        return Transformations.switchMap(postId, postsRepository::getPost)
    }

    fun markAsRead(id: String) {
        postsRepository.markPostAsRead(id)
    }
}