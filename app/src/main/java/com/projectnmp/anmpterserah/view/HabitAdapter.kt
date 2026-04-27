package com.projectnmp.anmpterserah.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectnmp.anmpterserah.databinding.HabitItemBinding
import com.projectnmp.anmpterserah.model.Habit
import com.projectnmp.anmpterserah.R
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel

class HabitAdapter(
    val habitList: ArrayList<Habit>,
    val viewModel: HabitViewModel,
    val userId: String
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(var binding: HabitItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        val binding = HabitItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HabitViewHolder,
        position: Int
    ) {
        val habit = habitList[position]

        /* Nama, deskripsi, progress value */
        holder.binding.txtHabitName.text = habit.name
        holder.binding.txtDesc.text = habit.description
        holder.binding.txtProgressValue.text =
            "${habit.currentProgress} / ${habit.goal} ${habit.unit}"

        /* Progress bar */
        val percentage = (habit.currentProgress * 100) / habit.goal
        holder.binding.progress.progress = percentage

        /* Badge status */
        if (habit.isCompleted) {
            holder.binding.txtStatus.text = "Completed"
            holder.binding.txtStatus.setBackgroundResource(R.drawable.bg_badge_completed)
            holder.binding.txtStatus.setTextColor(
                holder.binding.root.context.getColor(android.R.color.holo_green_dark)
            )
        } else {
            holder.binding.txtStatus.text = "In Progress"
            holder.binding.txtStatus.setBackgroundResource(R.drawable.bg_badge_progress)
            holder.binding.txtStatus.setTextColor(
                holder.binding.root.context.getColor(android.R.color.holo_purple)
            )
        }

        /* Icon habit */
        val iconRes = holder.binding.root.context.resources.getIdentifier(
            habit.icon, "drawable", holder.binding.root.context.packageName
        )
        if (iconRes != 0) {
            holder.binding.imgIcon.setImageResource(iconRes)
        }

        /* Tombol + dan - */
        holder.binding.btnIncrement.setOnClickListener {
            viewModel.incrementProgress(habit.id, userId)
        }
        holder.binding.btnDecrement.setOnClickListener {
            viewModel.decrementProgress(habit.id, userId)
        }

        /* Tombol delete */
        holder.binding.btnDelete.setOnClickListener {
            viewModel.deleteHabit(habit.id, userId)
        }
    }

    /* Refresh list habits */
    fun updateHabitList(newHabitList: List<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return habitList.size
    }
}