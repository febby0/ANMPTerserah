package com.projectnmp.anmpterserah.model

data class Habit(
    val id: Int,
    val userId: String,
    val name: String,
    val description: String,
    val goal: Int,
    val unit: String,
    val icon: String,
    var currentProgress: Int = 0
) {
    /* Status "Completed" / "In Progress" dihitung otomatis */
    val status: String
        get() = if (currentProgress >= goal) "Completed" else "In Progress"

    val isCompleted: Boolean
        get() = currentProgress >= goal
}
