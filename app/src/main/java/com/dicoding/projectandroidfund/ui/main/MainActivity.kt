package com.dicoding.projectandroidfund.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.projectandroidfund.R
import com.dicoding.projectandroidfund.adapter.AdapterMain
import com.dicoding.projectandroidfund.data.local.SettingPref
import com.dicoding.projectandroidfund.data.model.User
import com.dicoding.projectandroidfund.databinding.ActivityMainBinding
import com.dicoding.projectandroidfund.ui.setting.SettingAct
import com.dicoding.projectandroidfund.ui.detailUser.DetailUserActivity
import com.dicoding.projectandroidfund.ui.favorite.FavActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelMain
    private lateinit var adapter: AdapterMain
    private lateinit var tvMessage: TextView
    private val settingViewModel by viewModels<ViewModelMain> {
        ViewModelMain.Factory(SettingPref(this))
    }
    private fun navigateToDetailUser(data: User) {
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java).apply {
            putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
            putExtra(DetailUserActivity.EXTRA_ID, data.id)
            putExtra(DetailUserActivity.EXTRA_AVATARURL, data.avatar_url)
        }
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterMain()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : AdapterMain.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                navigateToDetailUser(data)
            }
        })

        settingViewModel.toastMessage.observe(this, { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        settingViewModel.getTheme().observe(this) {
            val nightMode = if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelMain::class.java)

        tvMessage = findViewById(R.id.tv_message)
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
            btnSearch.setOnClickListener {
                SearchUser()
            }
            etQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    SearchUser()
                    true
                } else {
                    false
                }
            }

        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                tvMessage.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun SearchUser() {
        val query = binding.etQuery.text.toString()
        if (query.isNotEmpty()) {
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fav -> startActivity(Intent(this, FavActivity::class.java))
            R.id.menu_setting -> startActivity(Intent(this, SettingAct::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
