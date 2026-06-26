package com.projectnmp.anmpterserah.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectnmp.anmpterserah.databinding.HabitItemBinding
import com.projectnmp.anmpterserah.model.Habit

class HabitAdapter(val habitList: ArrayList<Habit>, val listener: HabitItemListener)
    : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>(), HabitCardListener {

    class HabitViewHolder(var binding: HabitItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.binding.habit = habitList[position]
        holder.binding.listener = this
    }

    fun updateHabitList(newHabitList: List<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    override fun onIncrementClick(v: View) {
        val habitId = v.tag.toString().toInt()
        listener.onIncrementHabit(habitId)
    }

    override fun onDecrementClick(v: View) {
        val habitId = v.tag.toString().toInt()
        listener.onDecrementHabit(habitId)
    }

    override fun onDeleteClick(v: View) {
        val habitId = v.tag.toString().toInt()
        listener.onDeleteHabit(habitId)
    }

    override fun onHabitNameClick(v: View) {
        val habitId = v.tag.toString().toInt()
        listener.onEditHabit(v, habitId)
    }
}