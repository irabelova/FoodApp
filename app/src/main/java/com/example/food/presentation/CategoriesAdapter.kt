package com.example.food.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.R
import com.example.food.databinding.CategoryItemBinding
import com.example.food.domain.models.Category

class CategoriesAdapter
    (private val onSelectCategory: (Category) -> Unit): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categories: List<Category> = emptyList()
    private var selectedCategory: Category? = null

    class CategoryViewHolder(
        private val binding: CategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(category: Category, selectedCategory: Category?, onSelectCategory: (Category) -> Unit) {
            binding.categoryItem .setOnClickListener { onSelectCategory(category) }
            binding.categoryItem.text = category.displayName
            if(category == selectedCategory) {
                binding.root.backgroundTintList = ColorStateList.valueOf(binding.categoryItem.context.getColor(R.color.buttonBackground))
                binding.categoryItem.setBackgroundColor(binding.categoryItem.context.getColor(R.color.buttonBackground))
                binding.categoryItem.setTextColor(binding.categoryItem.context.getColor(R.color.outlineColor))
            } else {
                binding.root.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                binding.categoryItem.setBackgroundColor(Color.WHITE)
                binding.categoryItem.setTextColor(binding.categoryItem.context.getColor(R.color.inactiveTextButton))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(
            CategoryItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categories[position]
        holder.bind(item, selectedCategory, onSelectCategory)
    }

    fun updateCategories(categories: List<Category>) {
        if(categories != this.categories) {
            this.categories = categories
            notifyDataSetChanged()
        }
    }

    fun updateSelectedCategory(category: Category) {
        if(category != selectedCategory) {
            val previousSelected = selectedCategory
            selectedCategory = category
            if(previousSelected != null) {
                notifyItemChanged(categories.indexOf(previousSelected))
            }
            notifyItemChanged(categories.indexOf(category))
        }
    }
}