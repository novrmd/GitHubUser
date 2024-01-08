package com.dicoding.projectandroidfund.data.model

data class detailResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val repos_url: String,
    val name: String,
    val following: Int,
    val followers: Int,
    val public_repos: Int
)
