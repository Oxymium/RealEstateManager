package com.oxymium.realestatemanager.features.map

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentMapSelectedBinding
import com.oxymium.realestatemanager.viewmodel.MapSelectedViewModel


// Fragment tags
const val FRAGMENT_MAP_TAG = "MapFragment"
const val FRAGMENT_SELECTED_TAG = "SelectedFragment"
class MapSelectedFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentMapSelectedBinding: FragmentMapSelectedBinding
    private val binding get() = fragmentMapSelectedBinding

    // ViewModel
    private val mapSelectedViewModel: MapSelectedViewModel by activityViewModels()

    private var currentAnimator: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentMapSelectedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_selected, container, false)
        fragmentMapSelectedBinding.mapSelectedViewModel = mapSelectedViewModel
        fragmentMapSelectedBinding.fragmentSelectedExtras.mapSelectedViewModel = mapSelectedViewModel
        fragmentMapSelectedBinding.lifecycleOwner = activity

        // MAP FRAGMENT
        replaceFragment(MapFragment(), R.id.fragment_container_map, FRAGMENT_MAP_TAG)

        // SELECTED FRAGMENT
        replaceFragment(SelectedFragment(), R.id.fragment_container_selected, FRAGMENT_SELECTED_TAG)

        mapSelectedViewModel.iconHelper.observe(viewLifecycleOwner){
            if (it != null) {
                animateLoadingBarView()
            }
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment, containerId: Int, tag: String){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(containerId, fragment, tag)
        transaction?.commit()
    }

    private fun initLoadingBarView(){
        var duration = 2000L
        currentAnimator = ValueAnimator.ofFloat(0f, 1f)
        currentAnimator?.duration = duration
        currentAnimator?.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            fragmentMapSelectedBinding.loadingBarView.setProgress(progress)
        }
        // Listening to end animation event
        currentAnimator?.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                // Set value of helper to null to notify ViewModel
                mapSelectedViewModel.updateIconHelper(null)
            }
        })
    }
    private fun animateLoadingBarView(){
        currentAnimator?.removeAllListeners()
        currentAnimator?.cancel()
        initLoadingBarView()
        currentAnimator?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Reset selected value when Fragment is destroyed
        mapSelectedViewModel.updateSelectedEstate(null)
    }

}