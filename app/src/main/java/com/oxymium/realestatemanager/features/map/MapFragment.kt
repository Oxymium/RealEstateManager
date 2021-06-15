package com.oxymium.realestatemanager.features.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentMapBinding

// -----------
// MapFragment
// -----------

class MapFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentMapBinding: FragmentMapBinding
    private val binding get() = fragmentMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        return binding.root
    }

}