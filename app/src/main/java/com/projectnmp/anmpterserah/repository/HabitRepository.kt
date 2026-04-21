package com.projectnmp.anmpterserah.repository

import android.content.Context
import com.projectnmp.anmpterserah.model.Habit
import com.projectnmp.anmpterserah.util.HabitPreferences

class HabitRepository(val context: Context) {

    private val habitPrefs = HabitPreferences(context)

    init {
        // Isi seed data otomatis saat pertama kali app dijalankan
        habitPrefs.seedIfEmpty()
    }

    fun getHabits(userId: String): List<Habit> {
        return habitPrefs.getAllHabits(userId)
    }

    fun addHabit(habit: Habit): Habit {
        return habitPrefs.insertHabit(habit)
    }

    fun updateProgress(habitId: Int, action: String): Habit? {
        return habitPrefs.updateProgress(habitId, action)
    }

    fun deleteHabit(habitId: Int): Boolean {
        return habitPrefs.deleteHabit(habitId)
    }
}