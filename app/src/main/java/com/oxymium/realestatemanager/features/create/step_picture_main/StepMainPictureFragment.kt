package com.oxymium.realestatemanager.features.create.step_picture_main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentStepMainPictureBinding
import com.oxymium.realestatemanager.viewmodel.CreateViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StepMainPictureFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepMainPictureBinding: FragmentStepMainPictureBinding
    private val binding get() = stepMainPictureBinding

    private val createViewModel: CreateViewModel by activityViewModel<CreateViewModel>()

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