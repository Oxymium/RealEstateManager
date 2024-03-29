package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentDevBinding
import com.oxymium.realestatemanager.viewmodel.DevViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

// -----------
// DevFragment
// -----------

class DevFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentDevBinding: FragmentDevBinding
    private val binding get() = fragmentDevBinding

    // DevViewModel
    private val devViewModel: DevViewModel by activityViewModel<DevViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentDevBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dev, container, false)
        fragmentDevBinding.devViewModel = devViewModel
        fragmentDevBinding.lifecycleOwner = activity

        // RANDOM GIVEN INPUT
        fragmentDevBinding.devGenerateEstateNumberInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) devViewModel.updateGivenNumber(s.toString().toInt())
                    else devViewModel.updateGivenNumber(null)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        return binding.root
    }

}