package com.jaemin.hermes.book.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaemin.hermes.R
import com.jaemin.hermes.base.BaseViewBindingFragment
import com.jaemin.hermes.databinding.FragmentBookDetailBinding

class BookDetailFragment : BaseViewBindingFragment<FragmentBookDetailBinding>() {

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookDetailBinding {
       return FragmentBookDetailBinding.inflate(inflater, container, false)
    }


}