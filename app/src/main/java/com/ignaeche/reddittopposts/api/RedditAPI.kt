package com.ignaeche.reddittopposts.api

import com.ignaeche.reddittopposts.model.RedditResponse
import com.ignaeche.reddittopposts.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditAPI {
    companion object {
        val BASE_URL = "https://www.reddit.com/"
    }

    @GET("r/{subreddit}/top/.json?count={count}")
    fun getTopPosts(@Path("subreddit") subreddit: String = "all", @Path("count") count: Int = 50) : Call<RedditResponse<ResponseData>>
}