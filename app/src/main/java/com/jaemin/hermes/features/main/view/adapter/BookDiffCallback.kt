package com.jaemin.hermes.features.main.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jaemin.hermes.entity.Book

object BookDiffCallback : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
        oldItem == newItem

}