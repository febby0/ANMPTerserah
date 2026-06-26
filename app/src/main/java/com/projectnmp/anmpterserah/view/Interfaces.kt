package com.projectnmp.anmpterserah.view

import android.view.View

interface HabitCardListener {
    fun onIncrementClick(v: View)
    fun onDecrementClick(v: View)
    fun onDeleteClick(v: View)
    fun onHabitNameClick(v: View)
}

interface HabitItemListener {
    fun onIncrementHabit(habitId: Int)
    fun onDecrementHabit(habitId: Int)
    fun onDeleteHabit(habitId: Int)
    fun onEditHabit(v: View, habitId: Int)
}