package com.ngedev.thesisx.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ItemCategoryRowBinding
import com.ngedev.thesisx.utils.Category

class CategoryAdapter(private val listCategory: List<String>) :
    RecyclerView.Adapter<CategoryAdapter.HomeViewHolder>() {

    private var onItemSelectedListener: OnItemSelectedListener? = null
    private var categorySelected = 0

    fun setOnItemCallback(onItemSelected: (selectedCategory: String) -> Unit) {
        onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(category: String) {
                onItemSelected(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemCategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(listCategory[position], position)
        holder.itemView.setBackgroundResource(R.drawable.item_category_selected)
        if (categorySelected == position) {
            holder.itemView.setBackgroundResource(R.drawable.item_category_selected)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.item_category_unselected)
        }
    }

    override fun getItemCount(): Int = listCategory.size

    inner class HomeViewHolder(private val binding: ItemCategoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String, position: Int) {
            itemView.setOnClickListener {
                categorySelected = position
                onItemSelectedListener?.onItemSelected(category)

                notifyDataSetChanged()
            }

            binding.tvCategory.text = category
        }
    }
}

interface OnItemSelectedListener {
    fun onItemSelected(category: String)
}