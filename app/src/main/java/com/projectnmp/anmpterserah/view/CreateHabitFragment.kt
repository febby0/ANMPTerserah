package com.projectnmp.anmpterserah.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.projectnmp.anmpterserah.R
import com.projectnmp.anmpterserah.databinding.FragmentCreateHabitBinding
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel

class CreateHabitFragment : Fragment() {

    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var viewModel: HabitViewModel
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Ambil userId yang dikirim dari DashboardFragment */
        val args = CreateHabitFragmentArgs.fromBundle(requireArguments())
        userId = args.userId

        /* Setup Toolbar */
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            val action =
                CreateHabitFragmentDirections.actionCreateHabitFragmentToDashboardFragment(userId)
            it.findNavController().navigate(action)
        }

        viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        /* Setup spinner icon */
        val iconList =
            listOf("book", "fitness", "food", "meditation", "run", "sleep", "walk", "water")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, iconList)
        binding.spinnerIcon.setAdapter(adapter)
        binding.spinnerIcon.setText(iconList[0], false)

        binding.btnCreateHabit.setOnClickListener {
            val name = binding.txtName.text.toString()
            val desc = binding.txtDescription.text.toString()
            val goal = binding.txtGoal.text.toString()
            val unit = binding.txtUnit.text.toString()
            val icon = binding.spinnerIcon.text.toString()

            if (name.isEmpty() || desc.isEmpty() || goal.isEmpty() || unit.isEmpty() || icon.isEmpty()) {
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addHabit(userId, name, desc, goal.toInt(), unit, icon)

            val action = CreateHabitFragmentDirections.actionCreateHabitFragmentToDashboardFragment(userId)
            it.findNavController().navigate(action)
        }
    }
}