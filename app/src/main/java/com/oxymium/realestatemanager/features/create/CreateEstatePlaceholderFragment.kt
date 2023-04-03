package com.oxymium.realestatemanager.features.estates

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
import com.oxymium.realestatemanager.databinding.FragmentCreateEstatePlaceholderBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi


// ---------------
// EstatesFragment
// ---------------

class CreateEstatePlaceholderFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentCreateEstatePlaceholderBinding: FragmentCreateEstatePlaceholderBinding
    private val binding get() = fragmentCreateEstatePlaceholderBinding

    private val createEstateViewModel: CreateViewModel by activityViewModels() {
        CreateViewModelFactory(
            (activity?.application as EstatesApplication).repository3,
            (activity?.application as EstatesApplication).repository,
            (activity?.application as EstatesApplication).repository2
        )
    }

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

        fragmentCreateEstatePlaceholderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_estate_placeholder, container, false)
        fragmentCreateEstatePlaceholderBinding.lifecycleOwner = activity
        fragmentCreateEstatePlaceholderBinding.createEstateViewModel = createEstateViewModel

        // Return view
        return binding.root

    }
}





