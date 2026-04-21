package com.projectnmp.anmpterserah.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.projectnmp.anmpterserah.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val btnLogin = view.findViewById<Button>(R.id.btnLogin)
    val etUser = view.findViewById<EditText>(R.id.etUsername)
    val etPass = view.findViewById<EditText>(R.id.etPassword)
    val tvError = view.findViewById<TextView>(R.id.tvError)

    btnLogin.setOnClickListener {
      val user = etUser.text.toString()
      val pass = etPass.text.toString()

      if (user == "student" && pass == "123") {
        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
      } else {
        tvError.visibility = View.VISIBLE
        tvError.text = "Username / Password salah"
      }
    }
  }
}
