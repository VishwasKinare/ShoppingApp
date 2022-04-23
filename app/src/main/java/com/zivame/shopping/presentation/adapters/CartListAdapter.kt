package com.zivame.shopping.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zivame.shopping.R
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.databinding.CartListItemBinding

class CartListAdapter(val context: Context): RecyclerView.Adapter<CartListAdapter.NotificationsHolder>() {

    inner class NotificationsHolder(val binding: CartListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsHolder {
        return NotificationsHolder(
            CartListItemBinding.inflate(
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
                binding.amount.text = context.getString(R.string.rs_amount, getAmount(price, quantity).toString())
                binding.count.text = quantity.toString()
                binding.lessBtn.isEnabled = quantity > 1
                binding.moreBtn.isEnabled = quantity < 10
                Glide.with(context).asBitmap().load(img).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.productImage)
                binding.deleteBtn.setOnClickListener {
                    onRemoveClick?.let { it(product) }
                }
                binding.lessBtn.setOnClickListener {
                    val count = binding.count.text.toString().toInt() - 1
                    binding.count.text = "$count"
                    binding.lessBtn.isEnabled = count > 1
                    binding.moreBtn.isEnabled = count < 10
                    val amount = getAmount(price, count).toString()
                    binding.amount.text = context.getString(R.string.rs_amount, amount)
                    updateQuantity(product, count)
                }
                binding.moreBtn.setOnClickListener {
                    val count = binding.count.text.toString().toInt() + 1
                    binding.count.text = "$count"
                    binding.lessBtn.isEnabled = count > 1
                    binding.moreBtn.isEnabled = count < 10
                    val amount = getAmount(price, count).toString()
                    binding.amount.text = context.getString(R.string.rs_amount, amount)
                    updateQuantity(product, count)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CartItem>(){
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean{
            return oldItem == newItem
        }
    }

    val diffUtil =  AsyncListDiffer(this, diffCallback)

    private var onRemoveClick: ((CartItem) -> Unit)? = null
    private var onUpdateQuantity: ((CartItem, Int) -> Unit)? = null

    fun onRemoveClickListener(listener: (CartItem) -> Unit)  {
        onRemoveClick = listener
    }

    fun onUpdateQuantityListener(listener: (CartItem, Int) -> Unit)  {
        onUpdateQuantity = listener
    }

    private fun updateQuantity(item: CartItem, count: Int){
        onUpdateQuantity?.let { it(item, count) }
    }

    private fun getAmount(price: String, quantity:Int): Int{
        return price.toInt() * quantity
    }
}