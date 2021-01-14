package com.edapp.moviedatabase.api

import android.util.Log
import retrofit2.Response
import java.io.IOException

open class Repository {
    suspend fun <T : Any> makeApiCall(call: suspend() -> Response<T>, error: String): T? {
        val result = apiResult(call, error)
        var output : T? = null
        when (result) {
            is Result.Success -> output = result.output
            is Result.Error -> Log.e("Movie Repository", "$error")
        }
        return output
    }

    private suspend fun<T : Any> apiResult(call: suspend()-> Response<T>, error: String) : Result<T>{
        val response = call.invoke()
        return if (response.isSuccessful)
            Result.Success(response.body()!!)
        else
            Result.Error(IOException("failed to fetch movie list because of: $error"))
    }
}