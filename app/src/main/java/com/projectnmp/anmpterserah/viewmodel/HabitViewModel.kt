package com.projectnmp.anmpterserah.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.projectnmp.anmpterserah.model.Habit
import com.projectnmp.anmpterserah.repository.HabitRepository

class HabitViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = HabitRepository(application)

    val habitsLD = MutableLiveData<List<Habit>>()
    val errorLD = MutableLiveData<String>()

    /* Load semua habits milik user yang sedang login */
    fun loadHabits(userId: String) {
        val habits = repository.getHabits(userId)
        habitsLD.value = habits
    }

    /* Tambah habit baru */
    fun addHabit(
        userId: String, name: String, description: String,
        goal: Int, unit: String, icon: String
    ) {
        if (name.isBlank() || description.isBlank() || unit.isBlank() || goal <= 0) {
            errorLD.value = "Please fill in all fields and goal must be greater than 0"
            return
        }

        val newHabit = Habit(
            id = 0,   // nnti di assign otomatis oleh HabitPreferences
            userId = userId,
            name = name,
            description = description,
            goal = goal,
            unit = unit,
            icon = icon,
            currentProgress = 0
        )

        repository.addHabit(newHabit)
        loadHabits(userId)   // refresh list
    }

    /* Increment progress (click button +) */
    fun incrementProgress(habitId: Int, userId: String) {
        repository.updateProgress(habitId, "increment")
        loadHabits(userId)
    }

    /* Decrement progress (click button -) */
    fun decrementProgress(habitId: Int, userId: String) {
        repository.updateProgress(habitId, "decrement")
        loadHabits(userId)
    }

    /* Delete habit */
    fun deleteHabit(habitId: Int, userId: String) {
        repository.deleteHabit(habitId)
        loadHabits(userId)
    }
}
