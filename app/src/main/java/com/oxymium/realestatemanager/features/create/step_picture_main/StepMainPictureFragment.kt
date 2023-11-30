package com.oxymium.realestatemanager.features.create.step_picture_main

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
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentStepMainPictureBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.viewmodel.factories.CreateViewModelFactory

class StepMainPictureFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepMainPictureBinding: FragmentStepMainPictureBinding
    private val binding get() = stepMainPictureBinding

    private val createViewModel: CreateViewModel by activityViewModels {
        CreateViewModelFactory(
            (activity?.application as EstatesApplication).agentRepository,
            (activity?.application as EstatesApplication).estateRepository,
            (activity?.application as EstatesApplication).pictureRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stepMainPictureBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_main_picture, container, false)
        stepMainPictureBinding.lifecycleOwner = activity
        stepMainPictureBinding.createViewModel = createViewModel

        // Observe Main Picture and load into ImageView with Glide library
        createViewModel.estate.observe(viewLifecycleOwner) {
            it?.let {
                Glide
                    .with(this@StepMainPictureFragment)
                    .load(it.mainPicturePath)
                    .error(R.color.space_cadet)
                    .placeholder(R.color.space_cadet)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(stepMainPictureBinding.stepMainPicture)
            }
        }

        return binding.root
    }

}