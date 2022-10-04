package com.alexk.bidit.common.util.sharePreference

import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.alexk.bidit.BuildConfig
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.common.util.PUSH_TOKEN
import com.alexk.bidit.common.util.TOKEN

object UserTokenManager {

    private const val TAG = "TokenManager..."

    private val prefs: SharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            BuildConfig.APPLICATION_ID,
            masterKeyAlias,
            GlobalApplication.applicationContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getToken(): String {
        Log.d(TAG, "getToken = ${prefs.getString(TOKEN, "").toString()}")
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
        Log.d(TAG, "getPushToken = ${prefs.getString(TOKEN, "").toString()}")
        return prefs.getString(PUSH_TOKEN, "").toString()
    }

    fun setPushToken(value: String) {
        Log.d(TAG, "setPushToken = $value")
        prefs.edit().putString(PUSH_TOKEN, value).apply()
    }

    fun removePushToken() {
        prefs.edit().remove(PUSH_TOKEN).apply()
    }

}