package com.dicoding.projectandroidfund.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.projectandroidfund.data.local.FavUser

@Database(
    entities= [FavUser::class],
    version = 1
)

abstract class UserDB: RoomDatabase() {
    companion object{
        var INSTANCE: UserDB? = null
        fun getDatabase(context: Context): UserDB?{
            if (INSTANCE ==null){
                synchronized(UserDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDB::class.java, "user_db").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favUserDao(): FavUserDao
}