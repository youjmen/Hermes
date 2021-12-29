package com.jaemin.hermes.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<BINDING : ViewBinding> : AppCompatActivity() {
    private var _binding: BINDING? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = setViewBinding(layoutInflater)
        setContentView(binding.root)
    }


    abstract fun setViewBinding(inflater: LayoutInflater) : BINDING


}