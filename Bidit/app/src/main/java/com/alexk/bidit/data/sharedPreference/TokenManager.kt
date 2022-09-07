package com.alexk.bidit.data.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.alexk.bidit.BuildConfig
import com.alexk.bidit.common.util.PUSH_TOKEN
import com.alexk.bidit.common.util.TOKEN

class TokenManager(context: Context) {
    private val prefs: SharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

        EncryptedSharedPreferences.create(
            BuildConfig.APPLICATION_ID,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getToken(): String {
        Log.d(TAG, "getToken = ${prefs.getString(TOKEN,"").toString()}")
        return prefs.getString(TOKEN, "").toString()
    }

    fun setToken(value: String) {
        Log.d(TAG, "setToken = $value")
        prefs.edit().putString(TOKEN, value).apply()
    }

    fun removeToken() {
        prefs.edit().remove(TOKEN).apply()
    }

    fun getPushToken(): String {
        Log.d(TAG, "getPushToken = ${prefs.getString(TOKEN,"").toString()}")
        return prefs.getString(PUSH_TOKEN, "").toString()
    }

    fun setPushToken(value: String) {
        Log.d(TAG, "setPushToken = $value")
        prefs.edit().putString(PUSH_TOKEN, value).apply()
    }

    fun removePushToken() {
        prefs.edit().remove(PUSH_TOKEN).apply()
    }
    companion object{
        private const val TAG  = "TokenManager..."
    }
}