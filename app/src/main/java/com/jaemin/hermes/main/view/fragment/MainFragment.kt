package com.jaemin.hermes.main.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.jaemin.hermes.R
import com.jaemin.hermes.base.BaseViewBindingFragment
import com.jaemin.hermes.book.view.activity.BookActivity
import com.jaemin.hermes.book.view.fragment.BookDetailFragment
import com.jaemin.hermes.book.view.fragment.BookListFragment
import com.jaemin.hermes.databinding.FragmentMainBinding
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.main.view.BookThumbnailItemDecoration
import com.jaemin.hermes.main.view.ScaleViewPagerTransformer
import com.jaemin.hermes.main.view.activity.LocationRegisterActivity
import com.jaemin.hermes.main.view.activity.MainActivity
import com.jaemin.hermes.main.view.adapter.BookThumbnailAdapter
import com.jaemin.hermes.main.view.adapter.SpecialNewBooksAdapter
import com.jaemin.hermes.main.viewmodel.MainViewModel
import com.jaemin.hermes.util.toPx
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : BaseViewBindingFragment<FragmentMainBinding>(), BookThumbnailAdapter.OnItemClickListener {
    private val viewModel : MainViewModel by viewModel()
    private lateinit var bestSellerAdapter : BookThumbnailAdapter
    private lateinit var newBooksAdapter : BookThumbnailAdapter
    private lateinit var specialNewBooksAdapter : SpecialNewBooksAdapter

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.getBestSellers()
        viewModel.getNewBooks()
        viewModel.getNewSpecialBooks()
        with(viewModel){
            currentLocation.observe(viewLifecycleOwner){
                binding.tvLocation.text = it.roadAddress
            }
            bestSellers.observe(viewLifecycleOwner){
                bestSellerAdapter.submitList(it)
                binding.srlMain.isRefreshing = false
            }
            newBooks.observe(viewLifecycleOwner){
                newBooksAdapter.submitList(it)
                binding.srlMain.isRefreshing = false
            }
            newSpecialBooks.observe(viewLifecycleOwner){
                specialNewBooksAdapter.submitList(it)
                binding.vpNewSpecialBooks.currentItem = 1
                binding.srlMain.isRefreshing = false
            }
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.getCurrentLocation()
        binding.vpNewSpecialBooks.currentItem = 1
    }
    companion object{
        const val BOOK_NAME = "book_name"
    }

    override fun onItemClick(item: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out,R.anim.fade_in,R.anim.fade_out)
            .replace(R.id.fcv_main, BookDetailFragment().apply {
                val bundle = Bundle()
                bundle.putString(BookListFragment.ISBN, item)
                arguments = bundle
            })
            .addToBackStack(null)
            .commit()
    }
    private fun initView(){
        bestSellerAdapter = BookThumbnailAdapter(this)
        newBooksAdapter = BookThumbnailAdapter(this)
        val bookThumbnailItemDecoration = BookThumbnailItemDecoration(8f.toPx(requireContext()))

        with(binding){

            rvBestsellers.adapter = bestSellerAdapter
            rvNewBooks.adapter = newBooksAdapter

            rvBestsellers.addItemDecoration(bookThumbnailItemDecoration)
            rvNewBooks.addItemDecoration(bookThumbnailItemDecoration)

            etSearchBooks.setOnEditorActionListener { p0, actionId, p2 ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    startActivity(
                        Intent(requireActivity(), BookActivity::class.java)
                            .putExtra(BOOK_NAME,binding.etSearchBooks.text.toString()))
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            clLocation.setOnClickListener {
                startActivity(Intent(requireActivity(), LocationRegisterActivity::class.java))
            }
            srlMain.setOnRefreshListener {
                viewModel.getNewSpecialBooks()
                viewModel.getBestSellers()
                viewModel.getNewBooks()
            }
        }
        setSpecialNewBooksViewPager()
    }
    private fun setSpecialNewBooksViewPager(){
        specialNewBooksAdapter = SpecialNewBooksAdapter()
        binding.vpNewSpecialBooks.adapter = specialNewBooksAdapter

        val pageMarginPx = 8f.toPx(requireContext())
        val pagerWidth = 150f.toPx(requireContext())
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.vpNewSpecialBooks.offscreenPageLimit = 2
        binding.vpNewSpecialBooks.setPageTransformer(ScaleViewPagerTransformer(offsetPx))
    }

}