package com.ignaeche.reddittopposts.repository

import com.ignaeche.reddittopposts.repository.Status.ERROR
import com.ignaeche.reddittopposts.repository.Status.LOADING
import com.ignaeche.reddittopposts.repository.Status.SUCCESS

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T?): Resource<T> {
            return Resource(ERROR, data, message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
