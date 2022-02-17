package com.jaemin.hermes.book.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jaemin.hermes.R
import com.jaemin.hermes.book.view.fragment.BookDetailFragment
import com.jaemin.hermes.book.view.fragment.BookDetailFragment.Companion.FRAGMENT_CONTAINER_VIEW
import com.jaemin.hermes.book.view.fragment.BookListFragment
import com.jaemin.hermes.databinding.ActivityBookBinding
import com.jaemin.hermes.databinding.ActivityBookDetailBinding

class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.slide_in, R.anim.fade_out)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out,R.anim.fade_in,R.anim.fade_out)
            .replace(R.id.fcv_book_detail, BookDetailFragment().apply {
                val bundle = Bundle()
                bundle.putInt(FRAGMENT_CONTAINER_VIEW, R.id.fcv_book_detail)
                bundle.putString(BookListFragment.ISBN, intent?.getStringExtra(BookListFragment.ISBN))
                arguments = bundle
            })
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

    }

}