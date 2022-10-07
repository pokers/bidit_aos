package com.alexk.bidit.common.util.sharePreference

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.alexk.bidit.BuildConfig
import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.common.util.KEYWORD
import org.json.JSONArray

object SearchKeywordManager {

    private const val TAG = "SearchKeywordManager..."

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

    //받아온 sp를 ArrayList로 반환
    fun getKeyword(): ArrayList<String> {
        return decodeJSONArray(prefs.getString(KEYWORD, null))
    }

    //String으로 받은 sp를 ArrayList로 변환
    private fun decodeJSONArray(json: String?): ArrayList<String> {
        val keywordList = arrayListOf<String>()

        if (json != null) {
            val jsonArray = JSONArray(json)
            for (idx in 0 until jsonArray.length()) {
                keywordList.add(jsonArray.optString(idx))
            }
        }
        return keywordList
    }

    //sp에 추가
    fun addKeyword(baseList: ArrayList<String>, value: String) {
        val jsonArray = JSONArray()
        if(baseList.size == 10){
            baseList.removeAt(9)
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