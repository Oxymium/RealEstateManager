package com.oxymium.realestatemanager.features.create.step_nearby_places

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
import com.oxymium.realestatemanager.databinding.FragmentStepNearbyPlacesBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.features.create.LabelAdapter
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.toConcatenatedString
import com.oxymium.realestatemanager.utils.LabelListener
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

class StepNearbyPlacesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepNearbyPlacesBinding: FragmentStepNearbyPlacesBinding
    private val binding get() = stepNearbyPlacesBinding

    // RecyclerView
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

        stepNearbyPlacesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_nearby_places, container, false)
        stepNearbyPlacesBinding.lifecycleOwner = activity
        stepNearbyPlacesBinding.createViewModel = createViewModel

        // RecyclerView setup
        val gridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        stepNearbyPlacesBinding.stepNearbyPlacesRecyclerView.layoutManager = gridLayoutManager

        // Submit Nearby Places list
        createViewModel.nearbyPlaces.observe(viewLifecycleOwner){
            labelAdapter.submitList(it)
        }

        labelAdapter = LabelAdapter(
            LabelListener {
                // Update selected Label
                // TODO handle NearbyPlaces logic
                //createViewModel.updateEstateField(EstateField.NearbyPlaces(it.label))
                createViewModel.updateSelectedNearbyPlaces(it.label)
            }
        )

        createViewModel.selectedNearbyPlaces.observe(viewLifecycleOwner){ selectedLabels ->
            createViewModel.nearbyPlaces.value?.map { label ->
                if (selectedLabels.contains(label.label)) {
                    label.copy(isSelected = true)
                } else {
                    label.copy(isSelected = false)
                }
            }?.let { labels ->
                createViewModel.updateSelectedPlaces(labels)
                createViewModel.updateEstateField(EstateField.NearbyPlaces(labels.filter { it.isSelected }.toConcatenatedString() ) )
            }
        }

        // Adapter init
        stepNearbyPlacesBinding.stepNearbyPlacesRecyclerView.adapter = labelAdapter

        // RecyclerView side reached
        val onScrollToTop: () -> Unit = {
            createViewModel.updateReachedNearbyPlacesSide(ReachedSide.TopSide)
        }

        val onScrollToBottom: () -> Unit = {
            createViewModel.updateReachedNearbyPlacesSide(ReachedSide.BottomSide)
        }

        val scrollListener = RecyclerViewScrollListener(null, null, onScrollToTop, onScrollToBottom)
        stepNearbyPlacesBinding.stepNearbyPlacesRecyclerView.addOnScrollListener(scrollListener)

        return binding.root
    }
}