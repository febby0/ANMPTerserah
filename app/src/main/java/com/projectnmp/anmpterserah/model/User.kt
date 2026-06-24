package com.projectnmp.anmpterserah.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "password")
    var password: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
