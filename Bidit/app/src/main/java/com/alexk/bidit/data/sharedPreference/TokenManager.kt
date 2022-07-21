package com.alexk.bidit.data.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.alexk.bidit.BuildConfig
import com.alexk.bidit.common.util.PUSH_TOKEN
import com.alexk.bidit.common.util.TOKEN

class TokenManager(context: Context) {
    private val prefs: SharedPreferences by lazy {
        val masterKey =
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        EncryptedSharedPreferences.create(
            context,
            BuildConfig.APPLICATION_ID,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getToken(): String {
        Log.d("Get token",prefs.getString(TOKEN,"").toString())
        return prefs.getString(TOKEN, "").toString()
    }

    fun setToken(value: String) {
        Log.d("Set token",value)
        prefs.edit().putString(TOKEN, value).apply()
    }

    fun removeToken() {
        prefs.edit().remove(TOKEN).apply()
    }

    fun getPushToken(): String {
        Log.d("Get pushToken",prefs.getString(PUSH_TOKEN,"").toString())
        return prefs.getString(PUSH_TOKEN, "").toString()
    }

    fun setPushToken(value: String) {
        Log.d("Set pushToken",value)
        prefs.edit().putString(PUSH_TOKEN, value).apply()
    }

    fun removePushToken() {
        prefs.edit().remove(PUSH_TOKEN).apply()
    }
}