package com.jaemin.hermes.features.book.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaemin.hermes.R
import com.jaemin.hermes.features.book.view.fragment.BookListFragment
import com.jaemin.hermes.databinding.ActivityBookBinding
import com.jaemin.hermes.features.main.view.fragment.MainFragment

class BookActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fcv_book,BookListFragment().apply {
            arguments = Bundle().apply {
                putString(MainFragment.BOOK_NAME, intent.getStringExtra(MainFragment.BOOK_NAME))
            }
        }).commit()
    }
}