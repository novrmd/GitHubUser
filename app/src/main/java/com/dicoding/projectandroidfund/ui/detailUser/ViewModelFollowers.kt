package com.dicoding.projectandroidfund.ui.detailUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.projectandroidfund.networking.RetrofitClient
import com.dicoding.projectandroidfund.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFollowers: ViewModel() {
    val list_Followers = MutableLiveData<ArrayList<User>>()
    fun setListFollowers (username: String) {
        RetrofitClient.apiInstance
            .getFollowers (username)
            .enqueue(object: Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        list_Followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>>{
        return list_Followers
    }
}