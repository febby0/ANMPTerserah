package com.projectnmp.anmpterserah.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projectnmp.anmpterserah.databinding.HabitItemBinding
import com.projectnmp.anmpterserah.model.Habit
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel

class HabitAdapter(
  private val viewModel: HabitViewModel,
  private val userId: String
) : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

  private var list: List<Habit> = listOf()

  fun submitList(newList: List<Habit>) {
    list = newList
    notifyDataSetChanged()
  }

  inner class ViewHolder(val binding: HabitItemBinding)
    : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = HabitItemBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val habit = list[position]

    with(holder.binding) {

      // Set data
      txtHabitName.text = habit.name
      txtDesc.text = habit.description
      txtStatus.text = habit.status

      progress.max = habit.goal
      progress.progress = habit.currentProgress

      // Button +
      btnAdd.setOnClickListener {
        viewModel.incrementProgress(habit.id, userId)
      }

      // Button -
      btnSub.setOnClickListener {
        viewModel.decrementProgress(habit.id, userId)
      }
    }
  }

  override fun getItemCount() = list.size
}
