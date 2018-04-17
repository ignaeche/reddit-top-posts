package com.ignaeche.reddittopposts.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ignaeche.reddittopposts.model.PostData

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts(): LiveData<List<PostData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts : List<PostData>)

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPost(id : String) : LiveData<PostData>
}