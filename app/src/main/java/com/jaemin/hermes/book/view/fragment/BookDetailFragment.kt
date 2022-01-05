package com.jaemin.hermes.book.view.fragment

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
import com.jaemin.hermes.book.viewmodel.BookDetailViewModel
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
        arguments?.getString(BookListFragment.ISBN)?.let {
            viewModel.getBookInformation(it)
        }
        with(viewModel) {
            bookInformation.observe(viewLifecycleOwner) {
                setBookInformation(it)
            }

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


}