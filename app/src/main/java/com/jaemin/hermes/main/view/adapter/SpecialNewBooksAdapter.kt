package com.jaemin.hermes.main.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaemin.hermes.databinding.ItemSpecialBookBinding
import com.jaemin.hermes.entity.Book

class SpecialNewBooksAdapter : PagingDataAdapter<Book, SpecialNewBooksViewHolder>(BookDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialNewBooksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSpecialBookBinding.inflate(inflater, parent, false)
        return SpecialNewBooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecialNewBooksViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
class SpecialNewBooksViewHolder(private val binding : ItemSpecialBookBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : Book){
        Glide.with(binding.root)
            .load(item.cover)
            .into(binding.ivThumbnail)
        binding.tvBookName.text = item.title
        binding.tvAuthor.text = item.author
    }

}