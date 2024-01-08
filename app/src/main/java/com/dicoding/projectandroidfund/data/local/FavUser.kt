package com.dicoding.projectandroidfund.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "fav_user")
@Parcelize
data class FavUser(
    @ColumnInfo(name = "login")
    val login: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String,
    ): Parcelable

