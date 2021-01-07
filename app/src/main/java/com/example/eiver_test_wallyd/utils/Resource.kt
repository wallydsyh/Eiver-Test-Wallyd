package com.example.eiver_test_wallyd.utils

data class Resource<T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.Success, data, null)
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(Status.Error, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.Loading, data, null)
        }
    }

}