package com.projectnmp.anmpterserah.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.projectnmp.anmpterserah.databinding.FragmentEditHabitBinding
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel

class EditHabitFragment : Fragment() {

    private lateinit var binding: FragmentEditHabitBinding
    private lateinit var viewModel: HabitViewModel
    private lateinit var userId: String
    private var habitId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = EditHabitFragmentArgs.fromBundle(requireArguments())
        habitId = args.habitId
        userId = args.userId

        viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
        viewModel.loadHabit(habitId)

        /* Setup Toolbar */
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        /* Setup spinner icon */
        val iconList = listOf("book", "fitness", "food", "meditation", "run", "sleep", "walk", "water")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, iconList)
        binding.spinnerIcon.setAdapter(adapter)

        observeViewModel()

        binding.btnSubmit.setOnClickListener {
            val habit = binding.habit

            if (habit == null) {
                Toast.makeText(requireContext(), "Habit not loaded", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val goal = binding.txtGoal.text.toString()

            if (habit.name.isEmpty() || habit.description.isEmpty()
                || goal.isEmpty() || habit.unit.isEmpty()) {
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            habit.goal = goal.toInt()
            habit.icon = binding.spinnerIcon.text.toString()

            viewModel.updateHabit(habit, userId)
            Navigation.findNavController(it).popBackStack()
        }
    }

    fun observeViewModel() {
        viewModel.habitLD.observe(viewLifecycleOwner, Observer {
            binding.habit = it
            binding.spinnerIcon.setText(it.icon, false)
        })
    }
}