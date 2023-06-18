package com.example.food.presentation


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.food.R
import com.example.food.databinding.FoodItemBinding
import com.example.food.domain.models.Food

class FoodAdapter:  ListAdapter<Food, FoodAdapter.FoodViewHolder>(DiffCallback) {


    class FoodViewHolder(
        private val binding: FoodItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        
        fun bind(food: Food) {
            binding.foodTitle.text = food.name
            binding.foodDescription.text = food.description
            if (food.timeMinutes == 0 ){
                binding.foodTime.text = binding.root.context.getString(R.string.time)
            }
            else {
                binding.foodTime.text = binding.root.context.getString(
                    R.string.ready_in_minutes, food.timeMinutes)
            }
            binding.foodImage.load(
                food.thumbnailUrl,
            ) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
                listener(
                    onError = {request, result -> Log.e("LOADING_IMAGE", "request " + request, result.throwable)}
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FoodViewHolder(
            FoodItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Food>() {

        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }

    }
}