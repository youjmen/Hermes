package com.jaemin.hermes.book.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.jaemin.hermes.R
import com.jaemin.hermes.base.BaseViewBindingFragment
import com.jaemin.hermes.base.EventObserver
import com.jaemin.hermes.book.view.adapter.BookAdapter
import com.jaemin.hermes.book.viewmodel.BookViewModel
import com.jaemin.hermes.databinding.FragmentBookListBinding
import com.jaemin.hermes.main.view.activity.MainActivity
import com.jaemin.hermes.main.view.fragment.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookListFragment : BaseViewBindingFragment<FragmentBookListBinding>(), BookAdapter.OnItemClickListener {
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
        bookAdapter = BookAdapter(this)
        binding.rvBooks.adapter = bookAdapter

        arguments?.getString(MainFragment.BOOK_NAME)?.let {
            viewModel.searchBooks(it)
            binding.etSearchBooks.setText(it)
            arguments?.remove(MainFragment.BOOK_NAME)
        }
        binding.etSearchBooks.setOnEditorActionListener { p0, actionId, p2 ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.searchBooks(binding.etSearchBooks.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.srlBooks.setOnRefreshListener {
            viewModel.searchBooks(viewModel.bookName.value ?: binding.etSearchBooks.text.toString())
        }
        with(viewModel){
            books.observe(viewLifecycleOwner){
                bookAdapter.submitList(it)
                binding.tvEmptyBooks.visibility = View.GONE
                binding.pbLoadingBooks.visibility = View.GONE
                binding.srlBooks.isRefreshing = false

            }
            booksEmptyEvent.observe(viewLifecycleOwner, EventObserver{
                binding.tvEmptyBooks.visibility = View.VISIBLE
                binding.pbLoadingBooks.visibility =  View.GONE
                binding.srlBooks.isRefreshing = false

            })
            booksErrorEvent.observe(viewLifecycleOwner, EventObserver{
                binding.pbLoadingBooks.visibility =  View.GONE
                binding.srlBooks.isRefreshing = false
            })
            booksLoadingEvent.observe(viewLifecycleOwner, EventObserver{
                binding.pbLoadingBooks.visibility = View.VISIBLE
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onItemClick(item: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out,R.anim.fade_in,R.anim.fade_out)
            .replace(R.id.fcv_book, BookDetailFragment().apply {
                val bundle = Bundle()
                bundle.putString(ISBN, item)
                arguments = bundle
            })
            .addToBackStack(null)
            .commit()
    }
    companion object{
        const val ISBN = "ISBN"
    }


}