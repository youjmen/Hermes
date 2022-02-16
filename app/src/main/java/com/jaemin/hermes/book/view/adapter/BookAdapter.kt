package com.jaemin.hermes.book.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaemin.hermes.R
import com.jaemin.hermes.databinding.ItemBookBinding
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.main.view.adapter.BookDiffCallback

class BookAdapter(private val itemClickListener: OnItemClickListener) :
    PagingDataAdapter<Book, BookViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(layoutInflater, parent, false)

        return BookViewHolder(binding).apply {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { book->
                    itemClickListener.onItemClick(book.isbn)
                }
            }
        }
    }


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: String)
    }
}

class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(book: Book) {
        binding.tvBookName.text = book.title
        binding.tvAuthor.text = book.author
        binding.tvDescription.text = book.description
        binding.tvPrice.text = binding.root.context.getString(R.string.book_price, book.price)
        Glide.with(binding.ivThumbnail)
            .load(book.cover)
            .into(binding.ivThumbnail)

    }

}

