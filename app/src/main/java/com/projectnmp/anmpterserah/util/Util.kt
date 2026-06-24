package com.projectnmp.anmpterserah.util

import android.content.Context
import com.projectnmp.anmpterserah.model.HabitDatabase
import com.projectnmp.anmpterserah.model.User

val DB_NAME = "habit_tracker_db"

fun buildDb(context: Context): HabitDatabase {
    val db = HabitDatabase.buildDatabase(context)
    return db
}

fun seedUsers(context: Context){
    val db = buildDb(context)
    val users = arrayOf(
        User("febby", "febby123"),
        User("vanessa", "vanessa123"),
        User("aubrey", "aubrey123")
    )
    db.userDao().insertAll(*users)
}