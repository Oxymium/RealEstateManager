package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentToolsPlaceholderBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ToolsPlaceholderFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentToolsPlaceholderBinding: FragmentToolsPlaceholderBinding
    private val binding get() = fragmentToolsPlaceholderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentToolsPlaceholderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tools_placeholder, container, false)

        // Return view
        return binding.root

    }
}
