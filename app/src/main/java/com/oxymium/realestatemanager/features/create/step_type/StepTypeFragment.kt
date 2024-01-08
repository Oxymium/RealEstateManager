package com.oxymium.realestatemanager.features.create.step_type

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentStepTypesBinding
import com.oxymium.realestatemanager.features.create.LabelAdapter
import com.oxymium.realestatemanager.features.create.LabelListener
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.viewmodel.CreateViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StepTypeFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepTypesBinding: FragmentStepTypesBinding
    private val binding get() = stepTypesBinding

    // RecyclerView Adapter
    private lateinit var labelAdapter: LabelAdapter

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

        stepTypesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_step_types, container, false)
        stepTypesBinding.lifecycleOwner = activity
        stepTypesBinding.createViewModel = createViewModel

        // -----
        // TYPE
        // -----

        val gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        stepTypesBinding.stepTypeRecyclerView.layoutManager = gridLayoutManager

        labelAdapter = LabelAdapter(
            LabelListener { type ->
                createViewModel.updateSelectedType(type.label) // pass the title of the label
            }
        )

        // Types
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createViewModel.combinedTypes.collect {
                    labelAdapter.submitList(it)
                }
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