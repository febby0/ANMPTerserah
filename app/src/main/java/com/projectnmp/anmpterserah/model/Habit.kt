package com.projectnmp.anmpterserah.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habit(
    @ColumnInfo(name = "user_id")
    var userId: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "goal")
    var goal: Int,
    @ColumnInfo(name = "unit")
    var unit: String,
    @ColumnInfo(name = "icon")
    var icon: String,
    @ColumnInfo(name = "current_progress")
    var currentProgress: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    /* Status "Completed" / "In Progress" dihitung otomatis */
    val status: String
        get() = if (currentProgress >= goal) "Completed" else "In Progress"

    val isCompleted: Boolean
        get() = currentProgress >= goal
}
