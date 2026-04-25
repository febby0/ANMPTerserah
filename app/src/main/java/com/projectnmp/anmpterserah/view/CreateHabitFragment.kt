package com.projectnmp.anmpterserah.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectnmp.anmpterserah.R
import com.projectnmp.anmpterserah.databinding.FragmentCreateHabitBinding
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel

class CreateHabitFragment : Fragment(R.layout.fragment_create_habit) {

    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var viewModel: HabitViewModel
    private lateinit var userId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCreateHabitBinding.bind(view)

        val args = CreateHabitFragmentArgs.fromBundle(requireArguments())
        userId = args.userId

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        val iconList = listOf("Fitness", "Water", "Book", "Meditation")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, iconList)
        binding.spinnerIcon.setAdapter(adapter)

        binding.btnCreateHabit.setOnClickListener {
            val name = binding.txtName.text.toString()
            val desc = binding.txtDescription.text.toString()
            val goal = binding.txtGoal.text.toString()
            val unit = binding.txtUnit.text.toString()
            val icon = binding.spinnerIcon.text.toString()

            if (name.isEmpty() || desc.isEmpty() || goal.isEmpty() || unit.isEmpty()) {
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addHabit(userId, name, desc, goal.toInt(), unit, icon)

            findNavController().popBackStack()
        }
    }
}