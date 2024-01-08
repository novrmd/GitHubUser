package com.dicoding.projectandroidfund.data.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.projectandroidfund.data.local.FavUser

@Dao
interface FavUserDao {
    @Insert
     fun addToFavorite(favUser: FavUser)

    @Query("SELECT * FROM fav_user")
    fun getFavoriteUser(): LiveData<List<FavUser>>

    @Query("SELECT count(*) FROM fav_user WHERE fav_user.id=:id")
     fun checkUser(id: Int): Int

    @Query("DELETE FROM fav_user WHERE fav_user.id = :id")
     fun removeFromFavorite(id: Int): Int

    @Query("SELECT * FROM fav_user")
    fun findAll(): Cursor
}