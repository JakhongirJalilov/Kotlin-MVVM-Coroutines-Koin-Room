package com.example.registeruser.di

import android.content.Context
import android.util.Log
import com.example.registeruser.network.ApiInterface
import com.example.registeruser.utils.Constants
import com.google.gson.GsonBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Cache
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * @author jakhongirjalilov
 * @data 4/30/21
 * @project RegisterUser
 */

private const val CACHE_FILE_SIZE: Long = 3 * 1024 * 1024 // 3 Mib

@ExperimentalSerializationApi
val networkModule = module {
    single {
        retrofit(get(), Constants.BASE_URL)
    }

    single<Call.Factory> {
        val cacheFile = cacheFile(androidContext())
        val cache = cache(cacheFile)
        okhttp(cache)
    }

    single {
        get<Retrofit>().create(ApiInterface::class.java)
    }
}

@ExperimentalSerializationApi
private fun retrofit(callFactory: Call.Factory, baseUrl: String): Retrofit = Retrofit.Builder()
    .callFactory(callFactory)
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    .build()

fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.i("iTVNetworkCall##  %s", message) }
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
}


private fun cacheFile(context: Context) = File(context.filesDir, "itv_cache").apply {
    if (!this.exists())
        mkdirs()
}

private fun cache(cacheFile: File) = Cache(cacheFile, CACHE_FILE_SIZE)


private fun okhttp(cache: Cache) = OkHttpClient().newBuilder()
    .addInterceptor(httpLoggingInterceptor())
    .build()