package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentDevBinding
import com.oxymium.realestatemanager.databinding.FragmentToolsBinding
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import com.oxymium.realestatemanager.viewmodel.ToolsViewModel

// -----------
// DevFragment
// -----------

class DevFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentDevBinding: FragmentDevBinding
    private val binding get() = fragmentDevBinding

    // ViewModel
    private val estateViewModel: EstateViewModel by activityViewModels() {
        EstateViewModelFactory((activity?.application as EstatesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentDevBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dev, container, false)
        fragmentDevBinding.estateViewModel = estateViewModel


        return binding.root
    }

}