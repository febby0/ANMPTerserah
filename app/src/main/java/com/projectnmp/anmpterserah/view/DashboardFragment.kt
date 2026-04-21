package com.projectnmp.anmpterserah.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.projectnmp.anmpterserah.R
import com.projectnmp.anmpterserah.viewmodel.HabitViewModel


class DashboardFragment : Fragment() {

  private lateinit var viewModel: HabitViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    viewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]

    val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
    val fab = view.findViewById<FloatingActionButton>(R.id.fab)

    val adapter = HabitAdapter(viewModel)
    recycler.adapter = adapter

    viewModel.habitsLD.observe(viewLifecycleOwner) {
      adapter.submitList(it.toList())
    }

    fab.setOnClickListener {
      findNavController().navigate(R.id.actionDashboardtoCreateHabit)
    }
  }


}
