package com.example.kms.utils

interface Callback<T> {
    fun onSuccess(data: T)
    fun onError(throwable: Throwable)
}