package com.jaemin.hermes.main.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.jaemin.hermes.book.view.activity.BookActivity
import com.jaemin.hermes.databinding.ActivityMainBinding
import com.jaemin.hermes.main.view.fragment.LocationRegisterBottomSheetFragment
import com.jaemin.hermes.main.viewmodel.LocationRegisterViewModel
import com.jaemin.hermes.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getCurrentLocation()
        binding.etSearchBooks.setOnEditorActionListener { p0, actionId, p2 ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                startActivity(Intent(this, BookActivity::class.java)
                    .putExtra(BOOK_NAME,binding.etSearchBooks.text.toString()))
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.clLocation.setOnClickListener {
            LocationRegisterBottomSheetFragment().show(supportFragmentManager,LocationRegisterBottomSheetFragment.CLASS_NAME)
        }
        with(viewModel){
            currentLocation.observe(this@MainActivity){
                Log.d("ppplace",it.toString())
                binding.tvLocation.text = it.roadAddress
            }
        }

    }
    companion object{
        const val BOOK_NAME = "book_name"
    }
}