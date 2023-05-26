package com.example.mystoryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystoryapp.data.local.entity.StoryEntity
import com.example.mystoryapp.databinding.ItemStoryBinding
import com.example.mystoryapp.ui.home.HomeFragmentDirections

class StoryListAdapter :
    PagingDataAdapter<StoryEntity, StoryListAdapter.StoryViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            viewHolder.bind(data)
        }
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoryEntity) {
            Glide.with(itemView.context).load(data.photoUrl)
                .into(binding.imgItemPhoto)
            binding.tvTitle.text = data.name
            binding.tvDescription.text = data.description
            itemView.setOnClickListener {
                val action = HomeFragmentDirections.navigateToDetailStoryFragment(
                    data.photoUrl, data.name, data.description
                )
                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}