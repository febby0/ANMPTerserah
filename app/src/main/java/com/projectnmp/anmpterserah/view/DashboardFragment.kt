package com.projectnmp.anmpterserah.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectnmp.anmpterserah.databinding.FragmentDashboardBinding
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel

class DashboardFragment : Fragment(), HabitItemListener {

  private lateinit var viewModel: HabitViewModel
  private lateinit var binding: FragmentDashboardBinding
  private lateinit var userId: String
  private lateinit var habitAdapter: HabitAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentDashboardBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val args = DashboardFragmentArgs.fromBundle(requireArguments())
    userId = args.userId

    viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
    viewModel.loadHabits(userId)

    (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    binding.toolbar.title = "Hi, $userId!"

    /* Adapter terima this sebagai HabitItemListener dan userId untuk navigasi */
    habitAdapter = HabitAdapter(arrayListOf(), this, userId)

    binding.recyclerView.layoutManager = LinearLayoutManager(context)
    binding.recyclerView.adapter = habitAdapter

    observeViewModel()

    binding.fabAdd.setOnClickListener {
      val action = DashboardFragmentDirections
        .actionDashboardFragmentToCreateHabitFragment(userId)
      it.findNavController().navigate(action)
    }
  }

  fun observeViewModel() {
    viewModel.habitsLD.observe(viewLifecycleOwner, Observer {
      habitAdapter.updateHabitList(it)
      if (it.isEmpty()) {
        binding.recyclerView.visibility = View.GONE
        binding.layoutEmpty.visibility = View.VISIBLE
      } else {
        binding.recyclerView.visibility = View.VISIBLE
        binding.layoutEmpty.visibility = View.GONE
      }
    })
  }

  /* Implementasi HabitItemListener */

  override fun onIncrementHabit(habitId: Int) {
    viewModel.incrementProgress(habitId, userId)
  }

  override fun onDecrementHabit(habitId: Int) {
    viewModel.decrementProgress(habitId, userId)
  }

  override fun onDeleteHabit(habitId: Int) {
    viewModel.deleteHabit(habitId, userId)
  }
}
