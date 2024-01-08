package com.dicoding.projectandroidfund.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.projectandroidfund.data.local.FavUser
import com.dicoding.projectandroidfund.data.room.FavUserDao
import com.dicoding.projectandroidfund.data.room.UserDB

class FavViewModel (application: Application): AndroidViewModel(application){
    private var userDao: FavUserDao?
    private var userDb: UserDB?
    init {
        userDb = UserDB.getDatabase(application)
        userDao = userDb?. favUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavUser>>?{
        return userDao?.getFavoriteUser()
    }
}