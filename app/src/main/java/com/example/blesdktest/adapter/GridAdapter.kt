package com.example.blesdktest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blesdktest.databinding.ItemGridBinding

class GridAdapter(private val mGridData: List<String>) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    private lateinit var onItemCliCkCallback: OnItemCliCkCallback

    fun setOnItemClickCallback(onItemCliCkCallback: OnItemCliCkCallback) {
        this.onItemCliCkCallback = onItemCliCkCallback
    }

    class ViewHolder(val binding: ItemGridBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.gridbutton.text = mGridData[position]
        holder.itemView.setOnClickListener {
            this.onItemCliCkCallback.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int = mGridData.size

    interface OnItemCliCkCallback {
        fun onItemClicked(i: Int)
    }
}