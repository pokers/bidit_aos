package com.alexk.bidit.data.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.alexk.bidit.BuildConfig

const val keyword = "keyword"

class SearchKeywordManager(context: Context) {
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

    fun getKeyword(): MutableSet<String> {
        return prefs.getStringSet(keyword, mutableSetOf())!!
    }

    fun addKeyword(value: String) {
//        prefs.edit().putStringSet(keyword, value).apply()
    }

    fun removeKeyword() {
        prefs.edit().remove(keyword).apply()
    }
}