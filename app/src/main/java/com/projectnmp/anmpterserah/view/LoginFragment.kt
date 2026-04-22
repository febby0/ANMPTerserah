package com.projectnmp.anmpterserah.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.projectnmp.anmpterserah.R
import com.projectnmp.anmpterserah.databinding.FragmentLoginBinding
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

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        observeViewModel()

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            binding.txtError.visibility = View.GONE
            binding.textInputLayout2.error = null
            binding.textInputLayout4.error = null

            if (username.isEmpty()) {
                binding.textInputLayout2.error = "Username tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.textInputLayout4.error = "Password tidak boleh kosong"
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
                val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment(userId)
                binding.btnLogin.findNavController().navigate(action)
            } else {
                binding.txtError.visibility = View.VISIBLE
            }
        })
    }
}
