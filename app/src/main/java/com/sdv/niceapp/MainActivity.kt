package com.sdv.niceapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.sdv.niceapp.databinding.MainLayoutBinding
import kotlinx.android.synthetic.main.main_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MainLayoutBinding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        binding.activity = this

        setSupportActionBar(bottom_app_bar)
    }

    fun onFabClick(view: View) {
        Navigation.findNavController(this@MainActivity, R.id.fragment).navigate(R.id.action_global_searchFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom_app_bar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                Log.d("", "item = $item")
                BottomSheetDialog().show(supportFragmentManager, null)
            }
        }

        return true
    }
}
