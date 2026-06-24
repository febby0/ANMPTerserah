package com.projectnmp.anmpterserah.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun login(username: String, password: String): User?

}