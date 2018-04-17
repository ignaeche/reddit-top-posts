package com.ignaeche.reddittopposts.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.ignaeche.reddittopposts.model.PostData
import com.ignaeche.reddittopposts.model.UnreadPosts

@Dao
interface PostsDao {
    @Query("SELECT posts.*, unread_posts.unread FROM posts INNER JOIN unread_posts ON posts.id = unread_posts.id")
    fun getAllPosts(): LiveData<List<PostData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts : List<PostData>)

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPost(id : String) : LiveData<PostData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUnreadPosts(posts : List<UnreadPosts>)

    @Query("UPDATE unread_posts SET unread = :unread WHERE id = :id")
    fun markAsRead(id: String, unread: Boolean = false)
}