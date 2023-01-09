package com.example.blesdktest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blesdktest.databinding.ItemMainBinding
import com.inuker.bluetooth.library.search.SearchResult

class ListDeviceAdapter(private val listDevice: ArrayList<SearchResult>) :
    RecyclerView.Adapter<ListDeviceAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tv.text = listDevice[position].device.address
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listDevice[position])
        }
    }

    override fun getItemCount() = listDevice.size

    interface OnItemClickCallback {
        fun onItemClicked(searchResult: SearchResult)
    }
}