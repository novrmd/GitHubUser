package com.dicoding.projectandroidfund.ui.setting

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.projectandroidfund.data.local.SettingPref
import com.dicoding.projectandroidfund.databinding.ActivitySettingBinding

class SettingAct : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<ViewModelSetting> {
        ViewModelSetting.Factory(SettingPref(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }

        viewModel.getTheme().observe(this) { isDarkTheme ->
            val themeText = if (isDarkTheme) "Dark Theme" else "Light Theme"
            val nightMode = if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

            binding.switchTheme.text = themeText
            binding.switchTheme.isChecked = isDarkTheme
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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