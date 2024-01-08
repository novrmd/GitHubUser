package com.dicoding.projectandroidfund.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.projectandroidfund.data.local.SettingPref
import kotlinx.coroutines.launch

class ViewModelSetting(private val pref: SettingPref) : ViewModel() {

    fun getTheme() = pref.getThemeSetting().asLiveData()

    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDark)
        }
    }

    class Factory(private val pref: SettingPref) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ViewModelSetting(pref) as T
    }
}