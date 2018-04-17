package com.ignaeche.reddittopposts.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ignaeche.reddittopposts.model.PostData

@Database(entities = arrayOf(PostData::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        val DATABASE_NAME = "redditapp.db"
    }

    abstract fun postsDao() : PostsDao
}