package com.example.shopclothes.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopclothes.R
import com.example.shopclothes.databinding.CatalogPagerItemBinding
import com.example.domain.models.slide.Slide

class CatalogRootPagerAdapter(val list: List<com.example.domain.models.slide.Slide>) : RecyclerView.Adapter<CatalogRootPagerItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogRootPagerItem {
        val binding = CatalogPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CatalogRootPagerItem(binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CatalogRootPagerItem, position: Int) {

    }
}

class CatalogRootPagerItem(itemView: View) : RecyclerView.ViewHolder(itemView) {}