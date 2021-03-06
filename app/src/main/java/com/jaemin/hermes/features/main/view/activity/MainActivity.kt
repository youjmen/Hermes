package com.jaemin.hermes.features.main.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.jaemin.hermes.R
import com.jaemin.hermes.base.BaseViewBindingActivity
import com.jaemin.hermes.features.bookstore.BookstoreSearchFragment
import com.jaemin.hermes.databinding.ActivityMainBinding
import com.jaemin.hermes.features.main.view.fragment.MainFragment

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, MainFragment())
            .commit()
        binding.bnvMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_home->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fcv_main, MainFragment())
                        .commit()
                }
                R.id.item_find_bookstore->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fcv_main, BookstoreSearchFragment())
                        .commit()
                }


            }
            return@setOnItemSelectedListener true
        }
    }
    override fun setViewBinding(inflater: LayoutInflater): ActivityMainBinding {
       return ActivityMainBinding.inflate(inflater)
    }
}