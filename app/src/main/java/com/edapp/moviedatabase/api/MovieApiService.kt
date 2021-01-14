package com.edapp.moviedatabase.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieApiService {
    private val interceptor = Interceptor { chain ->
        val url = chain.request().url.newBuilder().addQueryParameter("api_key", API_KEY).build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    private val retrofit: Retrofit
        get() = Retrofit.Builder().client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    val movieApi: MovieApi
        get() = retrofit.create(MovieApi::class.java)

    private const val API_KEY = "a371f045b3ab77663a5c1143a9fb9da1"
    private const val BASE_URL = "https://api.themoviedb.org/3/"
}