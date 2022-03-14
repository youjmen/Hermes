package com.jaemin.hermes.features.main.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaemin.hermes.databinding.ItemBookThumbnailBinding
import com.jaemin.hermes.entity.Book

class BookThumbnailAdapter(private val itemClickListener: OnItemClickListener)
    : PagingDataAdapter<Book, BookThumbnailViewHolder>(BookDiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookThumbnailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookThumbnailBinding.inflate(inflater, parent, false)
        return BookThumbnailViewHolder(binding).apply {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { book->
                    itemClickListener.onItemClick(book.isbn)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BookThumbnailViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

}

class BookThumbnailViewHolder(private val binding : ItemBookThumbnailBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : Book){
        Glide.with(binding.root.context)
            .load(item.cover)
            .into(binding.ivThumbnail)
    }

}
