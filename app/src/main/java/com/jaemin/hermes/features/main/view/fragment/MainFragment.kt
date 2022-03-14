package com.jaemin.hermes.features.main.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import com.jaemin.hermes.base.BaseViewBindingFragment
import com.jaemin.hermes.features.book.view.activity.BookActivity
import com.jaemin.hermes.features.book.view.activity.BookDetailActivity
import com.jaemin.hermes.features.book.view.fragment.BookListFragment
import com.jaemin.hermes.databinding.FragmentMainBinding
import com.jaemin.hermes.features.main.view.BookThumbnailItemDecoration
import com.jaemin.hermes.features.main.view.ScaleViewPagerTransformer
import com.jaemin.hermes.features.main.view.activity.LocationRegisterActivity
import com.jaemin.hermes.features.main.view.adapter.BookThumbnailAdapter
import com.jaemin.hermes.features.main.view.adapter.SpecialNewBooksAdapter
import com.jaemin.hermes.features.main.viewmodel.MainViewModel
import com.jaemin.hermes.util.toPx
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : BaseViewBindingFragment<FragmentMainBinding>(), BookThumbnailAdapter.OnItemClickListener {
    private val viewModel : MainViewModel by viewModel()
    private lateinit var bestSellerAdapter : BookThumbnailAdapter
    private lateinit var newBooksAdapter : BookThumbnailAdapter
    private lateinit var specialNewBooksAdapter : SpecialNewBooksAdapter
    private val compositeDisposable = CompositeDisposable()
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
                if (it.roadAddress.isNotEmpty()){
                    binding.tvLocation.text = it.roadAddress
                }
                else{
                    binding.tvLocation.text = it.name
                }
            }
            bestSellers.observe(viewLifecycleOwner){
                bestSellerAdapter.submitData(lifecycle,it)
                binding.srlMain.isRefreshing = false
            }
            newBooks.observe(viewLifecycleOwner){
                newBooksAdapter.submitData(lifecycle,it)
                binding.srlMain.isRefreshing = false
            }
            newSpecialBooks.observe(viewLifecycleOwner){
                specialNewBooksAdapter.submitData(lifecycle,it)
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
        requireActivity().startActivity(Intent(requireActivity(), BookDetailActivity::class.java).apply {
            putExtra(BookListFragment.ISBN, item)
        })
    }
    private fun initView(){
        bestSellerAdapter = BookThumbnailAdapter(this)
        newBooksAdapter = BookThumbnailAdapter(this)
        val bookThumbnailItemDecoration = BookThumbnailItemDecoration(8f.toPx(requireContext()))

        with(binding){

            rvBestsellers.adapter = bestSellerAdapter
            rvNewBooks.adapter = newBooksAdapter

            newBooksAdapter.addLoadStateListener {
                if (it.source.refresh is LoadState.Error){
                    binding.clRetry.root.visibility = View.VISIBLE
                    binding.nsvMain.visibility = View.GONE
                    binding.pbMain.visibility = View.GONE

                }
                else if (it.source.refresh is LoadState.Loading){
                    binding.clRetry.root.visibility = View.GONE
                    binding.nsvMain.visibility = View.VISIBLE
                    binding.pbMain.visibility = View.VISIBLE
                }
                else if (it.source.refresh is LoadState.NotLoading){
                    binding.pbMain.visibility = View.GONE
                }
            }

            bestSellerAdapter.addLoadStateListener {
                if (it.source.refresh is LoadState.Error){
                    binding.clRetry.root.visibility = View.VISIBLE
                    binding.nsvMain.visibility = View.GONE
                    binding.pbMain.visibility = View.GONE
                }
                else if (it.source.refresh is LoadState.Loading){
                    binding.clRetry.root.visibility = View.GONE
                    binding.nsvMain.visibility = View.VISIBLE
                    binding.pbMain.visibility = View.VISIBLE

                }
                else if (it.source.refresh is LoadState.NotLoading){
                    binding.pbMain.visibility = View.GONE
                }
            }


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
            clRetry.ivRefresh.setOnClickListener {
                retryLoadingAllBooks()
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
        specialNewBooksAdapter.addLoadStateListener {
            if (it.source.refresh is LoadState.Error){
                binding.clRetry.root.visibility = View.VISIBLE
                binding.nsvMain.visibility = View.GONE
                binding.pbMain.visibility = View.GONE
            }
            else if (it.source.refresh is LoadState.Loading){
                binding.clRetry.root.visibility = View.GONE
                binding.nsvMain.visibility = View.VISIBLE
                binding.pbMain.visibility = View.VISIBLE
            }
            else if (it.source.refresh is LoadState.NotLoading){
                binding.pbMain.visibility = View.GONE
            }
        }

        val pageMarginPx = 8f.toPx(requireContext())
        val pagerWidth = 150f.toPx(requireContext())
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.vpNewSpecialBooks.offscreenPageLimit = 2
        binding.vpNewSpecialBooks.setPageTransformer(ScaleViewPagerTransformer(offsetPx))
    }
    private fun retryLoadingAllBooks(){
        retryLoadingBooks(bestSellerAdapter)
        retryLoadingBooks(newBooksAdapter)
        retryLoadingBooks(specialNewBooksAdapter)
    }
    private fun retryLoadingBooks(adapter: PagingDataAdapter<*,*>?){
        adapter?.retry()
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}