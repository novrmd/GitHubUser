package com.dicoding.projectandroidfund.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.dicoding.projectandroidfund.networking.RetrofitClient
import com.dicoding.projectandroidfund.data.local.SettingPref
import com.dicoding.projectandroidfund.data.model.User
import com.dicoding.projectandroidfund.data.model.userResponse
import retrofit2.Call
import retrofit2.Response

class ViewModelMain(private val preferences: SettingPref): ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    private fun showToast(message: String) {
        _toastMessage.postValue(message)
    }

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : retrofit2.Callback<userResponse> {
                override fun onResponse(
                    call: Call<userResponse>,
                    response: Response<userResponse>
                ) {
                    val userDetail = response.body()
                    if (userDetail != null) {
                        listUsers.postValue(response.body()?.items)
                    } else {
                        showToast("Failed to retrieve user data")
                    }
                }

                override fun onFailure(call: Call<userResponse>, t: Throwable) {
                    showToast("Request failed: ${t.message}")
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }

    class Factory(private val preferences: SettingPref) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ViewModelMain(preferences) as T
    }

}