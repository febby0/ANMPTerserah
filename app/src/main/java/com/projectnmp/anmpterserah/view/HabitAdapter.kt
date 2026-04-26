package com.projectnmp.anmpterserah.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectnmp.anmpterserah.databinding.HabitItemBinding
import com.projectnmp.anmpterserah.model.Habit
import com.projectnmp.anmpterserah.R

class HabitAdapter(val habitList: ArrayList<Habit>) :
    RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    var onIncrement: ((Int) -> Unit)? = null
    var onDecrement: ((Int) -> Unit)? = null

    var onDelete: ((Int) -> Unit)? = null

    class HabitViewHolder(var binding: HabitItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
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

        /* Progress bar — hitung persentase */
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
            onIncrement?.invoke(habit.id)
        }

        holder.binding.btnDecrement.setOnClickListener {
            onDecrement?.invoke(habit.id)
        }

        /* Tombol delete */
        holder.binding.btnDelete.setOnClickListener {
            onDelete?.invoke(habit.id)
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