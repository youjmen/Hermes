package com.jaemin.hermes.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFragment<BINDING : ViewBinding> : Fragment() {
    private var _binding: BINDING? = null
    protected val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setViewBinding(inflater, container)
        return binding.root
    }

    abstract fun setViewBinding(inflater: LayoutInflater, container: ViewGroup?) : BINDING

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}