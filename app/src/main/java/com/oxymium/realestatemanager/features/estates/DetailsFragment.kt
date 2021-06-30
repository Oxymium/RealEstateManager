package com.oxymium.realestatemanager.features.estates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentDetailsBinding
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory

// ---------------
// DetailsFragment
// ---------------

class DetailsFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    private val binding get() = fragmentDetailsBinding

    // EstateViewModel
    private lateinit var estateViewModelFactory: EstateViewModelFactory
    private lateinit var estateViewModel: EstateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        fragmentDetailsBinding.lifecycleOwner = activity
        //fragmentEstatesBinding.estateViewModel = estateViewModel

        return binding.root

    }

}