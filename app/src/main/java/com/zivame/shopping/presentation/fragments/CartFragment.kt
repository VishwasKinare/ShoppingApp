package com.zivame.shopping.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zivame.shopping.R
import com.zivame.shopping.databinding.FragmentCartBinding
import com.zivame.shopping.presentation.adapters.CartListAdapter
import com.zivame.shopping.presentation.viewmodels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment: Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private lateinit var cartListAdapter: CartListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        setListeners()
        setObservers()
    }

    private fun setListeners(){
        binding.checkoutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }

        cartListAdapter.onRemoveClickListener {
            viewModel.deleteFromCart(it)
        }

        var job: Job? = null

        cartListAdapter.onUpdateQuantityListener{ item, quantity ->
            job?.cancel()
            job = MainScope().launch {
                delay(200L)
                viewModel.updateCartCart(item.apply {
                    this.quantity = quantity
                })
            }
        }
    }

    private fun setObservers(){
        viewModel.cartList.observe(viewLifecycleOwner){
            if (it.isEmpty()){
                binding.errorLayout.visibility = View.VISIBLE
                binding.rvCartList.visibility = View.GONE
                binding.checkoutBtn.visibility = View.GONE
            } else {
                cartListAdapter.diffUtil.submitList(it)
                binding.errorLayout.visibility = View.GONE
                binding.rvCartList.visibility = View.VISIBLE
                binding.checkoutBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecycleView(){
        binding.rvCartList.apply {
            cartListAdapter = CartListAdapter(requireContext())
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}