package com.ignaeche.reddittopposts.repository

import android.arch.lifecycle.LiveData
import com.ignaeche.reddittopposts.AppExecutors
import com.ignaeche.reddittopposts.api.ApiResponse
import com.ignaeche.reddittopposts.api.RedditAPI
import com.ignaeche.reddittopposts.db.AppDatabase
import com.ignaeche.reddittopposts.model.PostData
import com.ignaeche.reddittopposts.model.RedditResponse
import com.ignaeche.reddittopposts.model.ResponseData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepository
    @Inject constructor(private val redditAPI: RedditAPI,
                        private val database: AppDatabase,
                        private val appExecutors: AppExecutors) {

    fun getPosts() : LiveData<Resource<List<PostData>>> {
        return object : NetworkBoundResource<List<PostData>, RedditResponse<ResponseData>>(appExecutors) {
            override fun saveCallResult(request: RedditResponse<ResponseData>) {
                database.postsDao().insertPosts(request.data.children.map { it.data })
            }

            override fun shouldFetch(data: List<PostData>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<PostData>> {
                return database.postsDao().getAllPosts()
            }

            override fun createCall(): LiveData<ApiResponse<RedditResponse<ResponseData>>> {
                return redditAPI.getTopPosts()
            }
        }.asLiveData()
    }
}