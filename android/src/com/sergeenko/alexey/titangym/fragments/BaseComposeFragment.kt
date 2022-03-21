package com.sergeenko.alexey.titangym.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class BaseComposeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext())
    }

    fun navigate(id: Int, args: Bundle? = null) {
        findNavController().navigate(id, args = args)
    }

    fun setContent(function: @Composable () -> Unit){
        (view as ComposeView).setContent(function)
    }

    open fun onBackPress(){
        activity?.onBackPressed()
    }
}
