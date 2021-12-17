package com.jaemin.hermes.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.jaemin.hermes.book.activity.BookActivity
import com.jaemin.hermes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etSearchBooks.setOnEditorActionListener { p0, actionId, p2 ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                startActivity(Intent(this, BookActivity::class.java)
                    .putExtra(BOOK_NAME,binding.etSearchBooks.text.toString()))
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
    companion object{
        const val BOOK_NAME = "book_name"
    }
}