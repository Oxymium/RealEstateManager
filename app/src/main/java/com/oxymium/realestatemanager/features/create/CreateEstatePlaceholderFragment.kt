package com.oxymium.realestatemanager.features.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentCreateEstatePlaceholderBinding
import com.oxymium.realestatemanager.viewmodel.CreateViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.activityViewModel


// ---------------
// EstatesFragment
// ---------------

class CreateEstatePlaceholderFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentCreateEstatePlaceholderBinding: FragmentCreateEstatePlaceholderBinding
    private val binding get() = fragmentCreateEstatePlaceholderBinding

    private val createEstateViewModel: CreateViewModel by activityViewModel<CreateViewModel>()

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





