package com.projectnmp.anmpterserah.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projectnmp.anmpterserah.util.DB_NAME

@Database(entities = arrayOf(Habit::class, User::class), version = 1)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: HabitDatabase? = null
        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                HabitDatabase::class.java,
                DB_NAME
            ).build()

        operator fun invoke(context: Context) {
            if (instance == null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }


}