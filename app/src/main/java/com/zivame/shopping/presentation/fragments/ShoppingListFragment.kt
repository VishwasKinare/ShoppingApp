package com.zivame.shopping.presentation.fragments

import android.animation.Animator
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zivame.shopping.R
import com.zivame.shopping.core.Utility.getMessage
import com.zivame.shopping.data.local.CartItem
import com.zivame.shopping.databinding.FragmentShoppingListBinding
import com.zivame.shopping.presentation.adapters.ShoppingListAdapter
import com.zivame.shopping.presentation.viewmodels.ShoppingViewModel
import com.zivame.shopping.utility.CircleAnimationUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShoppingListFragment: Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShoppingViewModel by viewModels()

    private lateinit var shoppingListAdapter: ShoppingListAdapter

    private var redCircle: FrameLayout? = null
    private var countTextView: TextView? = null
    private var cartImageView: ImageView? = null
    private var alertCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        setObservers()
        setListeners()
    }

    private fun setListeners(){
        shoppingListAdapter.onItemClickListener { it, imgView ->
            val item = CartItem(
                name = it.name,
                img = it.image_url,
                quantity = 1,
                price = it.price,
                timeStamp = System.currentTimeMillis()
            )
            makeFlyAnimation(imgView, item)
        }
    }

    private fun setObservers(){
        viewModel.cartItemCount.observe(viewLifecycleOwner){
            alertCount = it
            updateAlertIcon()
        }

        viewModel.shoppingList.observe(viewLifecycleOwner) {
            when {
                it.isLoading -> {
                    binding.shimmerViewContainer.visibility = VISIBLE
                    binding.shimmerViewContainer.startShimmer()
                    binding.rvShoppingList.visibility = GONE
                    binding.errorLayout.visibility = GONE
                }
                it.error != null -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = GONE
                    binding.rvShoppingList.visibility = GONE
                    binding.errorLayout.visibility = VISIBLE
                    binding.error.text = requireContext().getMessage(it.error)
                }
                else -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = GONE
                    it.data?.let { data ->
                        if (data.products.isEmpty()) {
                            binding.rvShoppingList.visibility = GONE
                            binding.errorLayout.visibility = VISIBLE
                            binding.error.text = getString(R.string.no_items_available)
                        } else {
                            shoppingListAdapter.diffUtil.submitList(data.products)
                            binding.rvShoppingList.visibility = VISIBLE
                            binding.errorLayout.visibility = GONE
                        }
                    } ?: kotlin.run {
                        binding.rvShoppingList.visibility = GONE
                        binding.errorLayout.visibility = VISIBLE
                        binding.error.text = getString(R.string.no_items_available)
                    }
                }
            }
        }
    }

    private fun setupRecycleView(){
        binding.rvShoppingList.apply {
            shoppingListAdapter = ShoppingListAdapter(requireContext())
            layoutManager = LinearLayoutManager(requireContext())
            adapter = shoppingListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val alertMenuItem = menu.findItem(R.id.action_cart)
        val rootView = alertMenuItem.actionView as FrameLayout

        redCircle = rootView.findViewById<View>(R.id.view_alert_red_circle) as FrameLayout
        cartImageView = rootView.findViewById<View>(R.id.cartImage) as ImageView
        countTextView = rootView.findViewById<View>(R.id.view_alert_count_textview) as TextView

        rootView.setOnClickListener { onOptionsItemSelected(alertMenuItem) }

        updateAlertIcon()
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart ->{
                findNavController().navigate(R.id.action_shoppingListFragment_to_cartFragment)
                return false
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeFlyAnimation(targetView: ImageView, item: CartItem) {
        CircleAnimationUtil().attachActivity(requireActivity()).setTargetView(targetView).setMoveDuration(600)
            .setDestView(cartImageView).setAnimationListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    viewModel.addToCart(item)
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationRepeat(animation: Animator?) {}

            }).startAnimation()
    }

    private fun updateAlertIcon() {
        if (alertCount in 1..99) {
            countTextView?.text = alertCount.toString()
        } else if (alertCount > 99){
            countTextView?.text = getString(R.string.max_notifications)
        }
        redCircle?.visibility = if (alertCount > 0) VISIBLE else GONE
    }

}