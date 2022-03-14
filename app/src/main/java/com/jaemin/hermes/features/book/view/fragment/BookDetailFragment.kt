package com.jaemin.hermes.features.book.view.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jaemin.hermes.R
import com.jaemin.hermes.base.BaseViewBindingFragment
import com.jaemin.hermes.base.EventObserver
import com.jaemin.hermes.features.book.view.data.BookUiModel
import com.jaemin.hermes.features.book.viewmodel.BookDetailViewModel
import com.jaemin.hermes.databinding.FragmentBookDetailBinding
import com.jaemin.hermes.entity.Book
import jp.wasabeef.blurry.Blurry
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDetailFragment : BaseViewBindingFragment<FragmentBookDetailBinding>() {
    private val viewModel: BookDetailViewModel by viewModel()
    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookDetailBinding {
        return FragmentBookDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBookInformation()
        binding.clCheckStock.setOnClickListener {
            val fcvId = arguments?.getInt(FRAGMENT_CONTAINER_VIEW)
            fcvId?.let {
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .replace(it, CheckStockFragment().apply {
                        val bundle = Bundle()
                        bundle.putParcelable(
                            BOOK_INFORMATION,
                            viewModel.bookInformation.value?.run {
                                BookUiModel(title, author, description, cover, price, isbn)
                            })
                        arguments = bundle

                    })
                    .commit()
            }

        }
        binding.clRetry.ivRefresh.setOnClickListener {
            getBookInformation()
        }
        with(viewModel) {
            bookInformation.observe(viewLifecycleOwner) {
                binding.pbLoading.visibility = View.GONE
                binding.clRetry.root.visibility = View.GONE
                setBookInformation(it)
            }
            bookInformationErrorEvent.observe(viewLifecycleOwner, EventObserver{
                binding.pbLoading.visibility = View.GONE
                binding.clRetry.root.visibility = View.VISIBLE
            })
            bookInformationLoadingEvent.observe(viewLifecycleOwner, EventObserver{
                binding.pbLoading.visibility = View.VISIBLE
            })

        }

    }

    private fun setBookInformation(book: Book) {
        Glide.with(requireContext())
            .load(book.cover)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Blurry.with(requireContext())
                        .sampling(100)
                        .from(resource!!.toBitmap())
                        .into(binding.ivBookThumbnailBackground)

                    return false
                }

            })

            .into(binding.ivBookThumbnail)


        binding.tvBookName.text = book.title
        binding.tvAuthor.text = book.author
        binding.tvDescription.text = book.description
        binding.tvPrice.text = getString(R.string.book_price_detail, book.price)

    }
    private fun getBookInformation(){
        arguments?.getString(BookListFragment.ISBN)?.let {
            viewModel.getBookInformation(it)
        }
    }
    companion object{
        const val BOOK_INFORMATION = "bookInformation"
        const val FRAGMENT_CONTAINER_VIEW = "FRAGMENT_CONTAINER_VIEW"
    }


}