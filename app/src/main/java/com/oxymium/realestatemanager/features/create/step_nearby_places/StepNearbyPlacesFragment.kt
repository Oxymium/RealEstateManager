package com.oxymium.realestatemanager.features.create.step_nearby_places

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentStepNearbyPlacesBinding
import com.oxymium.realestatemanager.features.create.NearbyPlaceAdapter
import com.oxymium.realestatemanager.features.create.NearbyPlaceListener
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.viewmodel.CreateViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StepNearbyPlacesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepNearbyPlacesBinding: FragmentStepNearbyPlacesBinding
    private val binding get() = stepNearbyPlacesBinding

    // RecyclerView
    private lateinit var nearbyPlaceAdapter: NearbyPlaceAdapter

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
        val gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        stepNearbyPlacesBinding.stepNearbyPlacesRecyclerView.layoutManager = gridLayoutManager


        nearbyPlaceAdapter = NearbyPlaceAdapter(
            NearbyPlaceListener {
                createViewModel.deleteNearbyPlaceInNearbyPlaces(it) // delete reference in the list
            }
        )

        createViewModel.nearbyPlaces.observe(viewLifecycleOwner) {
            nearbyPlaceAdapter.submitList(it)
        }

        // Adapter init
        stepNearbyPlacesBinding.stepNearbyPlacesRecyclerView.adapter = nearbyPlaceAdapter

        // RecyclerView side reached
        val onScrollToTop: () -> Unit = {
            createViewModel.updateReachedNearbyPlacesSide(ReachedSide.TopSide)
        }

        val onScrollToBottom: () -> Unit = {
            createViewModel.updateReachedNearbyPlacesSide(ReachedSide.BottomSide)
        }

        val scrollListener = RecyclerViewScrollListener(null, null, onScrollToTop, onScrollToBottom)
        stepNearbyPlacesBinding.stepNearbyPlacesRecyclerView.addOnScrollListener(scrollListener)

        // NEARBY PLACE
        stepNearbyPlacesBinding.stepNearbyPlaceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.updateNearbyPlaceStringValue(s.toString().replaceFirst(s[0], s[0].uppercaseChar())) // capitalize first letter
                    else createViewModel.updateNearbyPlaceStringValue(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        return binding.root
    }
}