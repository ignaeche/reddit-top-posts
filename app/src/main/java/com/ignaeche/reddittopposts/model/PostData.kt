package com.ignaeche.reddittopposts.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "posts")
data class PostData(@PrimaryKey
                    val id: String,
                    val title: String,
                    val author: String,
                    val created_utc: Int,
                    val thumbnail: String,
                    val num_comments: Int,
                    val unread: Boolean)
