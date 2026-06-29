package com.projectnmp.anmpterserah.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.projectnmp.anmpterserah.databinding.FragmentLoginBinding
import com.projectnmp.anmpterserah.util.SessionManager
import com.projectnmp.anmpterserah.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Cek session */
        val sessionManager = SessionManager(requireContext())
        if (sessionManager.isLoggedIn()) {
            val userId = sessionManager.getUserId() ?: ""
            val action = LoginFragmentDirections.actionDashboardFragment(userId)
            binding.root.findNavController().navigate(action)
            return
        }

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        observeViewModel()

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            binding.txtError.visibility = View.GONE

            if (username.isEmpty()) {
                binding.txtError.text = "Username tidak boleh kosong"
                binding.txtError.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.txtError.text = "Password tidak boleh kosong"
                binding.txtError.visibility = View.VISIBLE
                return@setOnClickListener
            }

            viewModel.login(username, password)
        }
    }

    fun observeViewModel() {
        viewModel.loginSuccessLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.txtError.visibility = View.GONE
                val userId = viewModel.userIdLD.value ?: ""
                val action = LoginFragmentDirections.actionDashboardFragment(userId)
                binding.btnLogin.findNavController().navigate(action)
            } else {
                binding.txtError.text = "Invalid username or password"
                binding.txtError.visibility = View.VISIBLE
            }
        })
    }
}
