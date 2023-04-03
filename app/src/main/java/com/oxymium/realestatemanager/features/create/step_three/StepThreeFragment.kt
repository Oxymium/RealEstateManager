package com.oxymium.realestatemanager.features.create.step_three

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
import com.oxymium.realestatemanager.databinding.FragmentStepThreeBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

// -----------------
// StepThreeFragment
// -----------------

class StepThreeFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepThreeBinding: FragmentStepThreeBinding
    private val binding get() = stepThreeBinding

    private val createViewModel: CreateViewModel by activityViewModels() {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stepThreeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_three, container, false)
        stepThreeBinding.lifecycleOwner = activity
        stepThreeBinding.createViewModel = createViewModel

        // Observe Main Picture and load into ImageView with Glide library
        createViewModel.mainPicturePath.observe(viewLifecycleOwner) {
            Glide
                .with(this@StepThreeFragment)
                .load(it)
                .error(R.drawable.dot_grid_repeating)
                .placeholder(R.drawable.dot_grid_repeating)
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(stepThreeBinding.stepThreeMainPicture)
        }

        return binding.root

    }

}