package com.alexk.bidit.data.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.alexk.bidit.BuildConfig
import com.alexk.bidit.common.util.KEYWORD
import org.json.JSONArray
import com.google.gson.JsonArray as JsonA

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

    //받아온 sp를 ArrayList로 반환
    fun getKeyword(): ArrayList<String> {
        return decodeJSONArray(prefs.getString(KEYWORD, null))
    }

    //String으로 받은 sp를 ArrayList로 변환
    fun decodeJSONArray(json: String?): ArrayList<String> {
        val keywordList = arrayListOf<String>()

        if (json != null) {
            val jsonArray = JSONArray(json)
            for (idx in 0 until jsonArray.length()) {
                keywordList.add(jsonArray.optString(idx))
            }
        }
        return keywordList;
    }

    //sp에 추가
    fun addKeyword(baseList: ArrayList<String>, value: String) {
        val jsonArray = JSONArray()
        if(baseList.size == 10){
            baseList.removeAt(29)
        }
        jsonArray.put(value)
        for (data in baseList.indices) {
            jsonArray.put(baseList[data])
        }
        prefs.edit().putString(KEYWORD, jsonArray.toString()).apply()
    }

    fun removeKeyword(baseList: ArrayList<String>) {
        val jsonArray = JSONArray()
        for (data in baseList.indices) {
            jsonArray.put(data)
        }
        prefs.edit().putString(KEYWORD, jsonArray.toString()).apply()
    }

    fun removeAllKeyword() {
        prefs.edit().remove(KEYWORD).apply()
    }
}