package com.projectnmp.anmpterserah.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectnmp.anmpterserah.model.Habit

class HabitPreferences(val context: Context) {

    companion object {
        private const val PREF_NAME = "habit_tracker_prefs"
        private const val KEY_HABITS = "habits"
        private const val KEY_NEXT_ID = "next_id"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    /* Ambil semua habits milik user tertentu */
    fun getAllHabits(userId: String): List<Habit> {
        val allHabits = getAllAllHabits()
        val result = ArrayList<Habit>()

        for (habit in allHabits) {
            if (habit.userId == userId) {
                result.add(habit)
            }
        }

        return result
    }

    /* Tambah habit baru */
    fun insertHabit(habit: Habit): Habit {
        val allHabits = getAllAllHabits()
        val newId = getNextId()
        val newHabit = habit.copy(id = newId)

        allHabits.add(newHabit)
        saveAllHabits(allHabits)
        incrementNextId()

        return newHabit
    }

    /* Update progress (increment/decrement) */
    fun updateProgress(habitId: Int, action: String): Habit? {
        val allHabits = getAllAllHabits()
        var index = -1

        for (i in 0 until allHabits.size) {
            if (allHabits[i].id == habitId) {
                index = i
                break
            }
        }

        if (index == -1) return null

        val habit = allHabits[index]
        var newProgress = habit.currentProgress

        if (action == "increment") {
            newProgress = minOf(habit.currentProgress + 1, habit.goal)
        } else if (action == "decrement") {
            newProgress = maxOf(habit.currentProgress - 1, 0)
        }

        val updatedHabit = habit.copy(currentProgress = newProgress)
        allHabits[index] = updatedHabit
        saveAllHabits(allHabits)

        return updatedHabit
    }

    /* Delete habit berdasarkan id */
    fun deleteHabit(habitId: Int): Boolean {
        val allHabits = getAllAllHabits()
        var indexToRemove = -1

        for (i in 0 until allHabits.size) {
            if (allHabits[i].id == habitId) {
                indexToRemove = i
                break
            }
        }

        if (indexToRemove == -1) return false

        allHabits.removeAt(indexToRemove)
        saveAllHabits(allHabits)
        return true
    }

    /* SEED: isi data awal jika belum ada data sama sekali */
    fun seedIfEmpty() {
        if (getAllAllHabits().isNotEmpty()) return

        val seedHabits = arrayListOf(
            Habit(1,  "febby",   "Drink Water",    "Stay hydrated throughout the day",  8,     "glasses", "water",      3),
            Habit(2,  "febby",   "Exercise",       "Daily workout routine",             30,    "minutes", "fitness",    15),
            Habit(3,  "febby",   "Read Books",     "Expand your knowledge",             20,    "pages",   "book",       20),
            Habit(4,  "febby",   "Meditation",     "Mindfulness practice",              10,    "minutes", "meditation", 0),

            Habit(5,  "vanessa", "Morning Walk",   "Walk every morning for health",     10000, "steps",   "walk",       4500),
            Habit(6,  "vanessa", "Drink Water",    "Stay hydrated every day",           8,     "glasses", "water",      8),
            Habit(7,  "vanessa", "Healthy Eating", "Eat nutritious meals every day",    3,     "meals",   "food",       1),
            Habit(8,  "vanessa", "Sleep Early",    "Get enough sleep every night",      8,     "hours",   "sleep",      0),

            Habit(9,  "aubrey",  "Read Books",     "Read at least 30 pages a day",      30,    "pages",   "book",       30),
            Habit(10, "aubrey",  "Meditation",     "Practice mindfulness every day",    15,    "minutes", "meditation", 7),
            Habit(11, "aubrey",  "Exercise",       "Stay active with daily workout",    45,    "minutes", "fitness",    0),
            Habit(12, "aubrey",  "Drink Water",    "Keep the body well hydrated",       8,     "glasses", "water",      3)
        )

        saveAllHabits(seedHabits)
        prefs.edit().putInt(KEY_NEXT_ID, 13).apply()
    }

    // Function helpers -----------------------------------
    private fun getAllAllHabits(): ArrayList<Habit> {
        val json = prefs.getString(KEY_HABITS, null) ?: return arrayListOf()
        val type = object : TypeToken<List<Habit>>() {}.type
        val list: List<Habit> = gson.fromJson(json, type)
        return ArrayList(list)
    }

    private fun saveAllHabits(habits: List<Habit>) {
        prefs.edit().putString(KEY_HABITS, gson.toJson(habits)).apply()
    }

    private fun getNextId(): Int {
        return prefs.getInt(KEY_NEXT_ID, 1)
    }

    private fun incrementNextId() {
        prefs.edit().putInt(KEY_NEXT_ID, getNextId() + 1).apply()
    }
}