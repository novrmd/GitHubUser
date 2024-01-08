package com.dicoding.projectandroidfund.ui.detailUser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.projectandroidfund.R
import com.dicoding.projectandroidfund.adapter.AdapterSectionPager
import com.dicoding.projectandroidfund.data.model.detailResponse
import com.dicoding.projectandroidfund.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATARURL = "extra_avatarurl"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATARURL)
        val profileUrl = "https://www.github.com/$username"
        val favoriteButton = binding.fabFavorite
        val shareButton = binding.actionShare
        val openButton = binding.actionOpen
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        showLoading(true)
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this, { user ->
            if (user != null) {
                bindUserData(user)
                showLoading(false)
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        runOnUiThread {
                            favoriteButton.setImageResource(R.drawable.ic_favorite)
                            _isChecked = true
                        }
                    } else {
                        runOnUiThread {
                            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
                            _isChecked = false
                        }
                    }
                }
            }
        }

        favoriteButton.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (avatarUrl != null && username != null) {
                        viewModel.addToFavorite(username, id, avatarUrl)
                        favoriteButton.setImageResource(R.drawable.ic_favorite)
                }
            } else {
                viewModel.deleteFromFavorite(id)
                favoriteButton.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val shareMessage = "See https://github.com/$username on GitHub!"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share profile via"))
        }
        openButton.setOnClickListener {
            val openIntent = Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl))
            startActivity(openIntent)
        }

        val adapterSectionPager = AdapterSectionPager(this, supportFragmentManager, bundle)
        binding.viewPager.adapter = adapterSectionPager
        binding.tab.setupWithViewPager(binding.viewPager)
    }

    private fun bindUserData(user: detailResponse) {
        binding.apply {
            Name.text = user.name
            Username.text = user.login
            tvFollowers.text = "${user.followers}"
            tvFollowing.text = "${user.following}"
            profile.loadImage(user.avatar_url)
            tvRepositories.text = "${user.public_repos}"
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
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

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this@DetailUserActivity)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(this)
    }
}
