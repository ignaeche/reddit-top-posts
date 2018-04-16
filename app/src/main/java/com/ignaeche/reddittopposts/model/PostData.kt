package com.ignaeche.reddittopposts.model

data class PostData(val title: String,
                    val author: String,
                    val created_utc: Int,
                    val thumbnail: String,
                    val num_comments: Int,
                    val unread: Boolean)
