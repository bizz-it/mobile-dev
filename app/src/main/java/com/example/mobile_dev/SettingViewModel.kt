package com.example.mobile_dev

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_dev.data.response.AuthResponse
import com.example.mobile_dev.data.response.UserResponse
import com.example.mobile_dev.ui.screen.home.HomeViewModel

import kotlinx.coroutines.launch

class SettingViewModel (private val pref: UserPreferences) : ViewModel() {
    fun getUserData(): LiveData<UserResponse> {
        return pref.getUserData().asLiveData()
    }

    fun saveUserData(userData: AuthResponse?) {
        viewModelScope.launch {
            pref.putUserData(userData)
        }
    }
    fun deleteData() {
        viewModelScope.launch {
            pref.deleteData()
        }
    }
}

class SettingFactory (private val pref: UserPreferences) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
//        else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//            return HomeViewModel(pref) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}