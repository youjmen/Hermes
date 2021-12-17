package com.jaemin.hermes.book.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaemin.hermes.book.viewmodel.BookViewModel
import com.jaemin.hermes.databinding.ActivityBookBinding
import com.jaemin.hermes.main.activity.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBookBinding
    private val viewModel : BookViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra(MainActivity.BOOK_NAME)?.let {
            viewModel.searchBooks(it)
        }
    }
}