package com.example.suitmediaapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.suitmediaapp.R
import com.example.suitmediaapp.databinding.ActivityScreenTwoBinding

class ScreenTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScreenTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // mengambil data lokal username untuk ditampilkan
        val savedUsername = loadUsername()
        savedUsername?.let {
            binding.txtUsername.text = it
        }

        // mengambil data dari screen lain kemudian ditampilkan
        val username = intent.getStringExtra(USERNAME_DATA)
        if (username != null){
            binding.txtSelected.text = username.toString()
        }

        setupAction()
    }

    private fun setupAction() {
        // ketika tombol kembali ditekan
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }

        // ketika tombole shoose user di tekan
        binding.btnChoose.setOnClickListener {
            // berpindah ke screen 3
            moveToThird()
        }
    }

    // fugnsi berpindah ke screen 3
    private fun moveToThird() {
        val intent = Intent(this@ScreenTwoActivity, ScreenThreeActivity::class.java)
        startActivity(intent)
    }

    // fungsi mengambil username
    private fun loadUsername(): String? {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    companion object{
        const val USERNAME_DATA = "username"
        const val PREFS_NAME = "my_prefs"
        const val KEY_USERNAME = "username"
    }
}