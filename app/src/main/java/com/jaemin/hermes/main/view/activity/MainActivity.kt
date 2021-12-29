package com.jaemin.hermes.main.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import com.jaemin.hermes.base.BaseViewBindingActivity
import com.jaemin.hermes.book.view.activity.BookActivity
import com.jaemin.hermes.databinding.ActivityMainBinding
import com.jaemin.hermes.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    private val viewModel : MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.etSearchBooks.setOnEditorActionListener { p0, actionId, p2 ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                startActivity(Intent(this, BookActivity::class.java)
                    .putExtra(BOOK_NAME,binding.etSearchBooks.text.toString()))
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.clLocation.setOnClickListener {
            startActivity(Intent(this, LocationRegisterActivity::class.java))
        }
        with(viewModel){
            currentLocation.observe(this@MainActivity){
                binding.tvLocation.text = it.roadAddress
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentLocation()
    }
    companion object{
        const val BOOK_NAME = "book_name"
    }

    override fun setViewBinding(inflater: LayoutInflater): ActivityMainBinding {
       return ActivityMainBinding.inflate(inflater)
    }
}