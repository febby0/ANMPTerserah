package com.projectnmp.anmpterserah.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg habit: Habit)

    @Query("SELECT * FROM habit WHERE user_id = :userId")
    fun selectAllHabits(userId: String): List<Habit>

    @Query("SELECT * FROM habit WHERE id = :id")
    fun selectHabit(id: Int): Habit

    @Query("UPDATE habit SET current_progress = :progress WHERE id = :id")
    fun updateProgress(id: Int, progress: Int)

    @Update
    fun updateHabit(habit: Habit)

    @Query("DELETE FROM habit WHERE id = :id")
    fun deleteHabit(id: Int)

}