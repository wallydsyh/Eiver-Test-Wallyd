package com.example.eiver_test_wallyd.utils

sealed class Status {
    object Loading : Status()
    object Success : Status()
    object Error : Status()
}