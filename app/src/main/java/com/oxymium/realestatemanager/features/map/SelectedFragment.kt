package com.oxymium.realestatemanager.features.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentSelectedBinding
import com.oxymium.realestatemanager.viewmodel.MapSelectedViewModel

class SelectedFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentSelectedBinding: FragmentSelectedBinding
    private val binding get() = fragmentSelectedBinding

    // ViewModel
    private val mapSelectedViewModel: MapSelectedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentSelectedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_selected, container, false)
        fragmentSelectedBinding.mapSelectedViewModel = mapSelectedViewModel
        fragmentSelectedBinding.lifecycleOwner = activity

        observeSelectedEstate()

        return binding.root

    }

    // Selected picture
    private fun observeSelectedEstate() {
        // Observe Main Picture and load into ImageView with Glide library
        mapSelectedViewModel.selectedEstate.observe(viewLifecycleOwner) {
            it?.let {
                Glide
                    .with(this@SelectedFragment)
                    .load(it.mainPicturePath)
                    .error(R.drawable.grid_dots_repeating)
                    .placeholder(R.drawable.grid_dots_repeating)
                    .transition(DrawableTransitionOptions.withCrossFade(1500))
                    .into(fragmentSelectedBinding.fragmentSelectedPicture)
            }
        }
    }
}