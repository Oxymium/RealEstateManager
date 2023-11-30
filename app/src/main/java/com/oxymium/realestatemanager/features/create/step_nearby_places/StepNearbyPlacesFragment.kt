package com.oxymium.realestatemanager.features.create.step_nearby_places

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentStepNearbyPlacesBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.features.create.LabelAdapter
import com.oxymium.realestatemanager.features.create.LabelListener
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.model.toConcatenatedString
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StepNearbyPlacesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepNearbyPlacesBinding: FragmentStepNearbyPlacesBinding
    private val binding get() = stepNearbyPlacesBinding

    // RecyclerView
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

        stepNearbyPlacesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_nearby_places, container, false)
        stepNearbyPlacesBinding.lifecycleOwner = activity
        stepNearbyPlacesBinding.createViewModel = createViewModel

        // RecyclerView setup
        val gridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        stepNearbyPlacesBinding.stepNearbyPlacesRecyclerView.layoutManager = gridLayoutManager

        // Submit Nearby Places list
        createViewModel.nearbyPlaces.observe(viewLifecycleOwner) { labels ->
            labelAdapter.submitList(labels)
            createViewModel.updateEstateField(EstateField.NearbyPlaces(labels.filter { it.isSelected }.toConcatenatedString())) // update the field with a String of all labels that are selected
        }

        labelAdapter = LabelAdapter(
            LabelListener { selectedLabel ->
                // Update selected Label
                val updatedList = createViewModel.nearbyPlaces.value?.map { label ->
                    if (label.label == selectedLabel.label) {
                        // Toggle isSelected for the selected label
                        label.copy(isSelected = !label.isSelected)
                    } else {
                        label
                    }
                }
                // Update nearby places
                createViewModel.updateNearbyPlaces(updatedList.orEmpty())
            }
        )

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