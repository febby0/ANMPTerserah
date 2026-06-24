package com.projectnmp.anmpterserah.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.projectnmp.anmpterserah.R
import com.projectnmp.anmpterserah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
      as NavHostFragment).navController
    NavigationUI.setupActionBarWithNavController(this, navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    return NavigationUI.navigateUp(navController, null)
  }
}
