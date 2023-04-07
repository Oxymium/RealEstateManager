package com.oxymium.realestatemanager.features.estates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentDetailsPlaceholderBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi


// ---------------
// EstatesFragment
// ---------------

class DetailsPlaceholderFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentDetailsPlaceholderBinding: FragmentDetailsPlaceholderBinding
    private val binding get() = fragmentDetailsPlaceholderBinding

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

        fragmentDetailsPlaceholderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_placeholder, container, false)

        // 360° rotation
        fragmentDetailsPlaceholderBinding.placeholderLogo
            .animate()
            .rotation(360f)
            .setDuration(10000L)
            .withEndAction {

            }
            .start()

        // Return view
        return binding.root

    }
}





