package com.projectnmp.anmpterserah.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val loginSuccessLD = MutableLiveData<Boolean>()
    val userIdLD = MutableLiveData<String>()

    fun login(username: String, password: String) {
        if (username == "febby" && password == "febby123") {
            userIdLD.value = username
            loginSuccessLD.value = true
        } else if (username == "vanessa" && password == "vanessa123") {
            userIdLD.value = username
            loginSuccessLD.value = true
        } else if (username == "aubrey" && password == "aubrey123") {
            userIdLD.value = username
            loginSuccessLD.value = true
        } else {
            loginSuccessLD.value = false
        }
    }
}