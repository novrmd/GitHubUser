package com.dicoding.projectandroidfund.networking

import com.dicoding.projectandroidfund.data.model.User
import com.dicoding.projectandroidfund.data.model.detailResponse
import com.dicoding.projectandroidfund.data.model.userResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<userResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @retrofit2.http.Path("username") username: String
    ): Call<detailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @retrofit2.http.Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @retrofit2.http.Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/public_repos")
    @Headers("Authorization: token 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")
    fun getRepository(
        @retrofit2.http.Path("username") username: String
    ): Call<detailResponse>

    @GET("users/{username}/html_url")
    @Headers("Authorization: token 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")
    fun getProfile(
        @retrofit2.http.Path("username") username: String
    ): Call<detailResponse>
}