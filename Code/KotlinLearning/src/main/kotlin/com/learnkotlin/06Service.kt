package com.learnkotlin

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class User(val login: String, val id: Long, val avatar_url: String)


interface GitHubService {
    @GET("https://api.github.com/repos/enbandari/Kotlin-Tutorials/startgazers")
    fun getStarGazers(): Call<List<User>>
}


object Service {
    val gitHubService: GitHubService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }
}


fun main() {
    Service.gitHubService.getStarGazers().execute().body().map(::println)
}
