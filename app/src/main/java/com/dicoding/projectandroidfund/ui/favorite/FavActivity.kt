package com.dicoding.projectandroidfund.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.projectandroidfund.R
import com.dicoding.projectandroidfund.data.local.FavUser
import com.dicoding.projectandroidfund.data.model.User
import com.dicoding.projectandroidfund.databinding.ActivityFavBinding
import com.dicoding.projectandroidfund.adapter.AdapterMain
import com.dicoding.projectandroidfund.ui.detailUser.DetailUserActivity

class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavBinding
    private lateinit var adapter : AdapterMain
    private lateinit var viewModel: FavViewModel
    private lateinit var tvMessage: TextView

    private fun navigateToDetailUser(data: User) {
        val intent = Intent(this@FavActivity, DetailUserActivity::class.java).apply {
            putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
            putExtra(DetailUserActivity.EXTRA_ID, data.id)
            putExtra(DetailUserActivity.EXTRA_AVATARURL, data.avatar_url)
        }
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterMain()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : AdapterMain.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                navigateToDetailUser(data)
            }
        })

        viewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        tvMessage = findViewById(R.id.tv_message)
        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavActivity)
            adapter = this@FavActivity.adapter
        }
        viewModel.getFavoriteUser()?.observe(this, { favUsers ->
            val userList = mapFavUserToUser(favUsers)
            adapter.setList(userList)
            tvMessage.visibility = if (favUsers.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    private fun mapFavUserToUser(favUsers: List<FavUser>): List<User> {
        return favUsers.map { favUser ->
            User(favUser.login, favUser.id, favUser.avatarUrl)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

