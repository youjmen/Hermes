package com.jaemin.hermes.main.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaemin.hermes.book.view.adapter.BookDiffCallback
import com.jaemin.hermes.databinding.ItemBookThumbnailBinding
import com.jaemin.hermes.databinding.ItemPlaceBinding
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.entity.Place

class BookThumbnailAdapter(private val itemClickListener: OnItemClickListener) : ListAdapter<Book, BookThumbnailViewHolder>(BookDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookThumbnailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookThumbnailBinding.inflate(inflater, parent, false)
        return BookThumbnailViewHolder(binding).apply {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(currentList[bindingAdapterPosition].isbn)
            }
        }
    }

    override fun onBindViewHolder(holder: BookThumbnailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

}

class BookThumbnailViewHolder(private val binding : ItemBookThumbnailBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : Book){
        Log.d("adsfasf", item.toString())
        Glide.with(binding.root.context)
            .load(item.cover)
            .into(binding.ivThumbnail)
    }

}
