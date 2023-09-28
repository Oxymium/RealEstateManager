package com.oxymium.realestatemanager.features.create.step_type

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentStepTypesBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.features.create.LabelAdapter
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.utils.LabelListener
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

class StepTypeFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepTypesBinding: FragmentStepTypesBinding
    private val binding get() = stepTypesBinding

    // RecyclerView Adapter
    private lateinit var labelAdapter: LabelAdapter

    private val createViewModel: CreateViewModel by activityViewModels {
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

        stepTypesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_step_types, container, false)
        stepTypesBinding.lifecycleOwner = activity
        stepTypesBinding.createViewModel = createViewModel

        // -----
        // TYPE
        // -----

        val gridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        stepTypesBinding.stepTypeRecyclerView.layoutManager = gridLayoutManager

        labelAdapter = LabelAdapter(
            LabelListener {
                // UPDATE Estate with Type
                createViewModel.updateEstateField(EstateField.Type(it.label))
            }
        )

        // Types
        createViewModel.types.observe(viewLifecycleOwner) { labelAdapter.submitList(it) }

        createViewModel.estate.observe(viewLifecycleOwner) { estate ->
            if (estate?.type == null) {
                createViewModel.updateTypes(createViewModel.types.value?.map { type ->
                    type.copy(isSelected = false)
                })
            } else {
                createViewModel.updateTypes(createViewModel.types.value?.map { type ->
                    type.copy(isSelected = type.label == estate.type)
                })
            }
        }

        // Adapter init
        stepTypesBinding.stepTypeRecyclerView.adapter = labelAdapter

        // RecyclerView side reached
        val onScrollToTop: () -> Unit = {
            createViewModel.updateReachedTypeSide(ReachedSide.TopSide)
        }

        val onScrollToBottom: () -> Unit = {
            createViewModel.updateReachedTypeSide(ReachedSide.BottomSide)
        }

        val scrollListener = RecyclerViewScrollListener(null, null, onScrollToTop, onScrollToBottom)
        stepTypesBinding.stepTypeRecyclerView.addOnScrollListener(scrollListener)

        return binding.root
    }
}