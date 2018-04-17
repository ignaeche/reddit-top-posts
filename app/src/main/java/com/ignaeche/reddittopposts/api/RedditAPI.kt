package com.ignaeche.reddittopposts.api

import android.arch.lifecycle.LiveData
import com.ignaeche.reddittopposts.model.RedditResponse
import com.ignaeche.reddittopposts.model.ResponseData
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditAPI {
    companion object {
        val BASE_URL = "https://www.reddit.com/"
    }

    @GET("r/{subreddit}/top/.json")
    fun getTopPosts(@Path("subreddit") subreddit: String = "all")
            : LiveData<ApiResponse<RedditResponse<ResponseData>>>
}