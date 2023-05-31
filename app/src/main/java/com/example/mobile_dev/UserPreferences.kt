package com.example.mobile_dev

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import com.example.mobile_dev.data.response.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.mobile_dev.data.response.UserResponse

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUserData(): Flow<UserResponse> {
        return dataStore.data.map { preferences ->
            UserResponse(preferences[TOKEN] ?: "",
                preferences[STATUS] ?: false,
                preferences[USERID] ?: "")
        }
    }

    suspend fun putUserData(user : AuthResponse?) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = user?.token.toString()
            preferences[STATUS] = user?.value?.isVerified as Boolean
            preferences[USERID] = user.value.id.toString()
        }
    }

    suspend fun deleteData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val TOKEN = stringPreferencesKey("tokenuser")
        private val FOTO = stringPreferencesKey("fotouser")
        private val NAME = stringPreferencesKey("nameuser")
        private val USERID = stringPreferencesKey("userid")
        private val EMAIL = stringPreferencesKey("emailuser")
        private val PLACE = stringPreferencesKey("placebirth")
        private val DATE = stringPreferencesKey("datebirth")
        private val STATUS = booleanPreferencesKey("status")
        private val TELF = stringPreferencesKey("telfuser")
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}