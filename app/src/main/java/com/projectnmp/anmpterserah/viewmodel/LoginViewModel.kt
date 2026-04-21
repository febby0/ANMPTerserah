package com.projectnmp.anmpterserah.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val loginSuccessLD = MutableLiveData<Pair<Boolean, String>>()
    // Pair: first = sukses/gagal, second = userId (username)

    // Hardcoded username & password
    private val validUsers = mapOf(
        "febby" to "febby123",
        "vanessa" to "vanessa123",
        "aubrey" to "aubrey123"
    )

    fun login(username: String, password: String) {
        val expectedPassword = validUsers[username]

        if (expectedPassword != null && expectedPassword == password) {
            loginSuccessLD.value = Pair(true, username)
        } else {
            loginSuccessLD.value = Pair(false, "")
        }
    }
}