package com.oxymium.realestatemanager.features.create.step_overview

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
import com.oxymium.realestatemanager.databinding.FragmentStepOverviewBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.viewmodel.factories.CreateViewModelFactory

class StepOverviewFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepOverviewBinding: FragmentStepOverviewBinding
    private val binding get() = stepOverviewBinding

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

        stepOverviewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_overview, container, false)
        stepOverviewBinding.lifecycleOwner = activity
        stepOverviewBinding.createViewModel = createViewModel


        return binding.root
    }

}