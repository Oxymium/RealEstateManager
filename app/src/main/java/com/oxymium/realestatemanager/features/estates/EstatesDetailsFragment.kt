package com.oxymium.realestatemanager.features.estates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentEstatesDetailsBinding
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.activityViewModel


// ---------------
// EstatesFragment
// ---------------

class EstatesDetailsFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentEstatesDetailsBinding: FragmentEstatesDetailsBinding
    private val binding get() = fragmentEstatesDetailsBinding

    // EstateViewModel
    private val estateViewModel: EstateViewModel by activityViewModel<EstateViewModel>()

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

        fragmentEstatesDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_estates_details, container, false)
        fragmentEstatesDetailsBinding.estateViewModel = estateViewModel

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_estates_details_left, EstatesFragment())
            .commit()

        // Observe Estate ID to query
        estateViewModel.selectedEstateId.observe(viewLifecycleOwner) {

            // Provide placeholder screen if no Estate is selected
            if (it == null || estateViewModel.estates.value.isEmpty()) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragment_estates_details_right, DetailsPlaceholderFragment())
                    .commit()
            }
            // Otherwise, load DetailsFragment
            else {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragment_estates_details_right, DetailsFragment())
                    .commit()
            }
        }

        // Return view
        return binding.root

    }
}





