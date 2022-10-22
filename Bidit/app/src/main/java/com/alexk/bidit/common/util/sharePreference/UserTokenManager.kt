package com.alexk.bidit.common.util.sharePreference

import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.alexk.bidit.BuildConfig
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.common.util.KEYWORD
import com.alexk.bidit.common.util.PUSH_TOKEN
import com.alexk.bidit.common.util.TOKEN
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.model.OAuthToken
import org.json.JSONArray

object UserTokenManager {

    private const val TAG = "TokenManager..."

    private val prefs: SharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        EncryptedSharedPreferences.create(
            BuildConfig.APPLICATION_ID,
            masterKeyAlias,
            GlobalApplication.instance.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getKakaoToken(): OAuthToken? {
        val getOAuthToken = prefs.getString(TOKEN, "")
        return GsonBuilder().create().fromJson(getOAuthToken, OAuthToken::class.java) ?: null
    }

    fun getKakaoAccessToken(): String? {
        return getKakaoToken()?.accessToken
    }

    fun setKakaoToken(oAuthToken: OAuthToken) {
        prefs.edit()
            .putString(TOKEN, GsonBuilder().create().toJson(oAuthToken, OAuthToken::class.java))
            .apply()
    }

    fun removeKakaoToken() {
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