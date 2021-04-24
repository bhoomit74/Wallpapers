package com.bhoomit.wallpapers.util

import java.lang.Exception

sealed class Result<out T> {
    class Success<out T>(val data : T) : Result<T>()
    class Error(val error : String) : Result<Nothing>()
    class Exception(val exception: java.lang.Exception) : Result<Nothing>()
}