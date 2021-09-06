package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentDevBinding
import com.oxymium.realestatemanager.databinding.FragmentToolsBinding
import com.oxymium.realestatemanager.viewmodel.*

// -----------
// DevFragment
// -----------

class DevFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentDevBinding: FragmentDevBinding
    private val binding get() = fragmentDevBinding

    // ViewModel
    private val createViewModel: CreateViewModel by activityViewModels() {
        CreateViewModelFactory((activity?.application as EstatesApplication).repository, (activity?.application as EstatesApplication).repository2)
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
        fragmentDevBinding.createViewModel = createViewModel

        return binding.root
    }

}