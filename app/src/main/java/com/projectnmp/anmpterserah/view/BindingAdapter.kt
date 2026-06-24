package com.projectnmp.anmpterserah.view

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.projectnmp.anmpterserah.R
import com.projectnmp.anmpterserah.model.Habit

@BindingAdapter("habitIcon")
fun loadHabitIcon(view: ImageView, iconName: String?) {
    if (iconName != null && iconName.isNotEmpty()) {
        val iconRes = view.context.resources.getIdentifier(
            iconName, "drawable", view.context.packageName
        )
        if (iconRes != 0) {
            view.setImageResource(iconRes)
        }
    }
}

@BindingAdapter("habitProgressBar")
fun setHabitProgressBar(view: ProgressBar, habit: Habit?) {
    if (habit != null && habit.goal > 0) {
        view.progress = (habit.currentProgress * 100) / habit.goal
    }
}

@BindingAdapter("habitProgressText")
fun setHabitProgressText(view: TextView, habit: Habit?) {
    if (habit != null) {
        view.text = "${habit.currentProgress} / ${habit.goal} ${habit.unit}"
    }
}

@BindingAdapter("habitStatus")
fun setHabitStatus(view: TextView, isCompleted: Boolean) {
    if (isCompleted) {
        view.text = "Completed"
        view.setBackgroundResource(R.drawable.bg_badge_completed)
        view.setTextColor(view.context.getColor(android.R.color.holo_green_dark))
    } else {
        view.text = "In Progress"
        view.setBackgroundResource(R.drawable.bg_badge_progress)
        view.setTextColor(view.context.getColor(android.R.color.holo_purple))
    }
}