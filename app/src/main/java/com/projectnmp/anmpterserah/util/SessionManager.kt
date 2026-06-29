package com.projectnmp.anmpterserah.util

import android.content.Context

class SessionManager(context: Context) {

    companion object {
        private const val PREF_NAME = "habit_session"
        private const val KEY_USER_ID = "user_id"
    }

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveSession(userId: String) {
        prefs.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        return getUserId() != null
    }
}