package com.jaemin.hermes.book.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaemin.hermes.base.BaseViewBindingFragment
import com.jaemin.hermes.book.view.adapter.BookAdapter
import com.jaemin.hermes.book.viewmodel.BookViewModel
import com.jaemin.hermes.databinding.FragmentBookListBinding
import com.jaemin.hermes.main.activity.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookListFragment : BaseViewBindingFragment<FragmentBookListBinding>() {
    private val viewModel : BookViewModel by viewModel()
    private lateinit var bookAdapter: BookAdapter
    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookListBinding {
        return FragmentBookListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookAdapter = BookAdapter()
        binding.rvBooks.adapter = bookAdapter

        arguments?.getString(MainActivity.BOOK_NAME)?.let {
            viewModel.searchBooks(it)
        }
        with(viewModel){
            viewModel.books.observe(viewLifecycleOwner){
                bookAdapter.submitList(it)
            }
        }

    }


}