package com.dicoding.projectandroidfund.ui.detailUser

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.projectandroidfund.networking.RetrofitClient
import com.dicoding.projectandroidfund.data.local.FavUser
import com.dicoding.projectandroidfund.data.room.FavUserDao
import com.dicoding.projectandroidfund.data.room.UserDB
import com.dicoding.projectandroidfund.data.model.detailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<detailResponse>()

    private val userDao: FavUserDao = UserDB.getDatabase(application)?.favUserDao()
        ?: throw IllegalStateException("UserDB is null, check your database initialization.")


    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<detailResponse> {
                override fun onResponse(
                    call: Call<detailResponse>,
                    response: Response<detailResponse>
                ) {
                    if (response.isSuccessful) {
                        val userDetail = response.body()
                        if (userDetail != null) {
                            user.postValue(response.body())
                        } else {
                            showToast("Response body is null")
                        }
                    } else {
                        showToast("Failed to retrieve user data")
                    }
                }

                override fun onFailure(call: Call<detailResponse>, t: Throwable) {
                    showToast("Request failed: ${t.message}")
                }
            })
    }


    fun getUserDetail(): LiveData<detailResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavUser(username, id, avatarUrl)
            userDao?.addToFavorite(user)
            showToast("User added to favorite")
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun deleteFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
            showToast("User deleted from favorite")
        }
    }

    private fun showToast(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
        }
    }
}