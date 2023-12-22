package com.example.suitmediaapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.suitmediaapp.R
import com.example.suitmediaapp.databinding.ActivityScreenOneBinding

class ScreenOneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScreenOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        // fungsi ketika tombol tombol check ditekan
        binding.btnCheck.setOnClickListener {
            val text: String = binding.etPalindrome.text.toString()
            if (!TextUtils.isEmpty(text)){
                // ketika edit text tidak kosong maka akan melakukan check palindrom
                palinCheck(text)
            }else{
                // jika kosong maka akan muncul alert
                showAlert(getString(R.string.palin_check) ,getString(R.string.text_empty))
            }
        }

        binding.btnNext.setOnClickListener {
            val name: String = binding.etName.text.toString()
            if (!TextUtils.isEmpty(name)){
                // ketika edit text tidak kosong maka akan melakukan save username dan berpindah screen
                saveUsername(name)
                moveToSecond(name)
            }else{
                // jika kosong maka akan muncul alert
                showAlert(getString(R.string.name_check) ,getString(R.string.name_empty))
            }

        }
    }

    // fungsi untuk melakukan cek palindrome
    private fun palinCheck(text: String) {
        val cleanText = text.replace("\\s".toRegex(), "")
        val reversedText = cleanText.reversed()
        val message = if (cleanText == reversedText) {
            "$text isPalindrome."
        } else {
            "$text not palindrome."
        }

        // memunculkan alert hasil dari pengecekan
        showAlert(getString(R.string.palin_check), message)
    }

    // fungsi berpindah ke screen dua
    private fun moveToSecond(name: String) {
        val intent = Intent(this@ScreenOneActivity, ScreenTwoActivity::class.java)
        startActivity(intent)
    }

    // fungsi untuk save username
    private fun saveUsername(username: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    // fungsi untuk menampilkan alert
    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    companion object{
        const val PREFS_NAME = "my_prefs"
        const val KEY_USERNAME = "username"
    }
}