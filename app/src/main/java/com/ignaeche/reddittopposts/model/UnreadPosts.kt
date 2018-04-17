package com.ignaeche.reddittopposts.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "unread_posts")
data class UnreadPosts(@PrimaryKey
                       val id: String,
                       val unread: Boolean = true)