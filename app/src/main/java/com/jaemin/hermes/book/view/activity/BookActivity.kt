package com.jaemin.hermes.book.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaemin.hermes.R
import com.jaemin.hermes.book.view.fragment.BookListFragment
import com.jaemin.hermes.databinding.ActivityBookBinding
import com.jaemin.hermes.main.view.activity.MainActivity

class BookActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fcv_book,BookListFragment().apply {
            arguments = Bundle().apply {
                putString(MainActivity.BOOK_NAME, intent.getStringExtra(MainActivity.BOOK_NAME))
            }
        }).commit()
    }
}