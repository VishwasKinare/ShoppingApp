package com.zivame.shopping.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zivame.shopping.R
import com.zivame.shopping.databinding.FragmentCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutFragment: Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setViews()
    }

    private fun setListeners(){
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutFragment_to_shoppingListFragment)
        }
    }

    private fun setViews(){
        MainScope().launch(Dispatchers.IO) {
            delay(20000L)
            launch(Dispatchers.Main) {
                binding.cartAnimationView.visibility = View.GONE
                binding.successView.visibility = View.VISIBLE
                binding.continueBtn.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}