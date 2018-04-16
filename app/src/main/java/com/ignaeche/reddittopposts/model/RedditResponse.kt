package com.ignaeche.reddittopposts.model

data class RedditResponse<T>(val kind: String,
                             val data: T)