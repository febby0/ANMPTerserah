package com.projectnmp.anmpterserah.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projectnmp.anmpterserah.util.buildDb
import com.projectnmp.anmpterserah.util.seedUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val loginSuccessLD = MutableLiveData<Boolean>()
    val userIdLD = MutableLiveData<String>()

    /* Isi data user awal saat app pertama kali dijalankan */
    init {
        launch {
            seedUsers(getApplication())
        }
    }

    fun login(username: String, password: String) {
        launch {
            val db = buildDb(getApplication())
            val user = db.userDao().login(username, password)
            if (user != null) {
                userIdLD.postValue(user.username)
                loginSuccessLD.postValue(true)
            } else {
                loginSuccessLD.postValue(false)
            }
        }
    }


}