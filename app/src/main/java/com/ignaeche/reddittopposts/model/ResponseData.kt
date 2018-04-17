package com.ignaeche.reddittopposts.model

data class ResponseData(val after: String,
                        val before: String?,
                        val modhash: String,
                        val children: List<RedditResponse<PostData>>)