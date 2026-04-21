package com.projectnmp.anmpterserah.view

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projectnmp.anmpterserah.model.Habit
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel

class HabitAdapter(private val viewModel: HabitViewModel)
  : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

  private var list = listOf<Habit>()

  fun submitList(newList: List<Habit>) {
    list = newList
    notifyDataSetChanged()
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val btnPlus: Button = view.findViewById(R.id.btnPlus)
    val btnMinus: Button = view.findViewById(R.id.btnMinus)
    val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    val status: TextView = view.findViewById(R.id.tvStatus)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val habit = list[position]

    holder.progressBar.max = habit.goal
    holder.progressBar.progress = habit.progress

    holder.status.text =
      if (habit.progress >= habit.goal) "Completed"
      else "In Progress"

    holder.btnPlus.setOnClickListener {
      viewModel.updateProgress(habit, 1)
    }

    holder.btnMinus.setOnClickListener {
      viewModel.updateProgress(habit, -1)
    }
  }

  override fun getItemCount() = list.size
}
