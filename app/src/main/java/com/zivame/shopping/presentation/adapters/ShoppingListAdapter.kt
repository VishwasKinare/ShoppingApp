package com.zivame.shopping.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zivame.shopping.R
import com.zivame.shopping.data.dto.Product
import com.zivame.shopping.databinding.ShoppingListItemBinding

class ShoppingListAdapter(val context: Context): RecyclerView.Adapter<ShoppingListAdapter.NotificationsHolder>() {

    inner class NotificationsHolder(val binding: ShoppingListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsHolder {
        return NotificationsHolder(
            ShoppingListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationsHolder, position: Int) {
        val product = diffUtil.currentList[position]
        with(holder) {
            with(product) {
                binding.title.text = name
                binding.price.text = context.getString(R.string.rs_amount, price)
                binding.ratingBar.rating = rating.toFloat()
                Glide.with(context).load(image_url).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.productImage)
                binding.addToCartBtn.setOnClickListener {
                    onItemClickClick?.let { it(product, binding.productImage) }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean{
            return oldItem == newItem
        }
    }

    val diffUtil =  AsyncListDiffer(this, diffCallback)

    private var onItemClickClick: ((Product, ImageView) -> Unit)? = null

    fun onItemClickListener(listener: (Product, ImageView) -> Unit)  {
        onItemClickClick = listener
    }
}