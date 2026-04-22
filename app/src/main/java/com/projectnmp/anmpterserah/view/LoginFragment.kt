package com.projectnmp.anmpterserah.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectnmp.anmpterserah.R
import com.projectnmp.anmpterserah.databinding.FragmentLoginBinding
import com.projectnmp.anmpterserah.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

  private var _binding: FragmentLoginBinding? = null
  private val binding get() = _binding!!

  private lateinit var viewModel: LoginViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentLoginBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

    // Klik tombol login
    binding.btnLogin.setOnClickListener {
      val user = binding.etUsername.text.toString()
      val pass = binding.etPassword.text.toString()

      viewModel.login(user, pass)
    }

    // Observe hasil login
    viewModel.loginSuccessLD.observe(viewLifecycleOwner) { result ->
      if (result.first) {
        // sukses
        binding.tvError.visibility = View.GONE

        val userId = result.second

        // kirim userId ke dashboard (opsional, tapi bagus)
        val action = LoginFragmentDirections
          .actionLoginFragmentToDashboardFragment(userId)

        findNavController().navigate(action)

      } else {
        // gagal
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = "Username / Password salah"
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
