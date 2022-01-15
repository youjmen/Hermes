package com.jaemin.hermes.main.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaemin.hermes.databinding.ItemPlaceBinding
import com.jaemin.hermes.entity.Place

class PlaceAdapter(private val itemClickListener: OnItemClickListener) : ListAdapter<Place, PlaceViewHolder>(PlaceDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater, parent, false)
        return PlaceViewHolder(binding).apply {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(currentList[bindingAdapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    interface OnItemClickListener {
        fun onItemClick(item: Place)
    }

}

class PlaceViewHolder(private val binding : ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(place : Place){
        binding.tvLocationName.text = place.name
        binding.tvLocationAddress.text = place.roadAddress
    }

}
class PlaceDiffCallback : DiffUtil.ItemCallback<Place>(){
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean  =
        oldItem == newItem

}