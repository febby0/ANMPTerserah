package com.projectnmp.anmpterserah.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.projectnmp.anmpterserah.model.Habit
import com.projectnmp.anmpterserah.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HabitViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    val habitsLD = MutableLiveData<List<Habit>>()
    val habitLD = MutableLiveData<Habit>()

    /* Load semua habits */
    fun loadHabits(userId: String) {
        launch {
            val db = buildDb(getApplication())
            habitsLD.postValue(db.habitDao().selectAllHabits(userId))
        }
    }

    /* Load 1 habit */
    fun loadHabit(id: Int) {
        launch {
            val db = buildDb(getApplication())
            habitLD.postValue(db.habitDao().selectHabit(id))
        }
    }

    fun addHabit(
        userId: String, name: String, description: String,
        goal: Int, unit: String, icon: String
    ) {
        val newHabit = Habit(
            userId = userId,
            name = name,
            description = description,
            goal = goal,
            unit = unit,
            icon = icon,
            currentProgress = 0
        )
        launch {
            val db = buildDb(getApplication())
            db.habitDao().insertAll(newHabit)
            habitsLD.postValue(db.habitDao().selectAllHabits(userId))
        }
    }

    fun updateHabit(habit: Habit, userId: String) {
        launch {
            val db = buildDb(getApplication())
            db.habitDao().updateHabit(habit)
            habitsLD.postValue(db.habitDao().selectAllHabits(userId))
        }
    }

    fun incrementProgress(habitId: Int, userId: String) {
        launch {
            val db = buildDb(getApplication())
            val habit = db.habitDao().selectHabit(habitId)
            val newProgress = minOf(habit.currentProgress + 1, habit.goal)
            db.habitDao().updateProgress(habitId, newProgress)
            habitsLD.postValue(db.habitDao().selectAllHabits(userId))
        }
    }

    fun decrementProgress(habitId: Int, userId: String) {
        launch {
            val db = buildDb(getApplication())
            val habit = db.habitDao().selectHabit(habitId)
            val newProgress = maxOf(habit.currentProgress - 1, 0)
            db.habitDao().updateProgress(habitId, newProgress)
            habitsLD.postValue(db.habitDao().selectAllHabits(userId))
        }
    }

    fun deleteHabit(habitId: Int, userId: String) {
        launch {
            val db = buildDb(getApplication())
            db.habitDao().deleteHabit(habitId)
            habitsLD.postValue(db.habitDao().selectAllHabits(userId))
        }
    }


}
