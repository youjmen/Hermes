package com.jaemin.hermes.book.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaemin.hermes.databinding.ItemBookBinding
import com.jaemin.hermes.entity.Book

class BookAdapter : ListAdapter<Book, BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(layoutInflater, parent, false)
        return BookViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(currentList[position])

    }
}

class BookViewHolder(private val binding : ItemBookBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(book : Book){
        binding.tvBookName.text = book.title
        binding.tvAuthor.text = book.author
        Glide.with(binding.ivThumbnail)
            .load(book.cover)
            .into(binding.ivThumbnail)

    }

}
class BookDiffCallback : DiffUtil.ItemCallback<Book>(){

    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean  =
        oldItem == newItem

}