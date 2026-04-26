package com.projectnmp.anmpterserah.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectnmp.anmpterserah.databinding.FragmentDashboardBinding
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel


class DashboardFragment : Fragment() {

  private lateinit var viewModel: HabitViewModel
  private val habitAdapter = HabitAdapter(arrayListOf())
  private lateinit var binding: FragmentDashboardBinding
  private lateinit var userId: String

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentDashboardBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    /* Ambil userId yang dikirim dari LoginFragment */
    val args = DashboardFragmentArgs.fromBundle(requireArguments())
    userId = args.userId

    viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
    viewModel.loadHabits(userId)

    /* Setup Toolbar */
    (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    binding.toolbar.title = "Hi, $userId!"

    /* Setup RecyclerView */
    binding.recyclerView.layoutManager = LinearLayoutManager(context)
    binding.recyclerView.adapter = habitAdapter

    observeViewModel()

    /* FAB : navigasi ke CreateHabitFragment, kirim userId */
    binding.fabAdd.setOnClickListener {
      val action = DashboardFragmentDirections.actionDashboardFragmentToCreateHabitFragment(userId)
      it.findNavController().navigate(action)
    }

    /* Callback dari adapter saat tombol + ditekan */
    habitAdapter.onIncrement = { habitId ->
      viewModel.incrementProgress(habitId, userId)
    }

    /* Callback dari adapter saat tombol - ditekan */
    habitAdapter.onDecrement = { habitId ->
      viewModel.decrementProgress(habitId, userId)
    }

    /* Tombol delete */
    habitAdapter.onDelete = { habitId ->
      viewModel.deleteHabit(habitId, userId)
    }
  }

  fun observeViewModel() {
    viewModel.habitsLD.observe(viewLifecycleOwner, Observer {
      habitAdapter.updateHabitList(it)

      /* Tampilkan empty state kalau belum ada habit */
      if (it.isEmpty()) {
        binding.recyclerView.visibility = View.GONE
        binding.layoutEmpty.visibility = View.VISIBLE
      } else {
        binding.recyclerView.visibility = View.VISIBLE
        binding.layoutEmpty.visibility = View.GONE
      }
    })
  }
}
