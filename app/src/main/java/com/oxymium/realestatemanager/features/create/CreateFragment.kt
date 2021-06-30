package com.oxymium.realestatemanager.features.create

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentCreateBinding
import com.oxymium.realestatemanager.viewmodel.CreateViewModel

// --------------
// CreateFragment
// --------------

class CreateFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentCreateBinding: FragmentCreateBinding
    private val binding get() = fragmentCreateBinding

    private val createViewModel: CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentCreateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)
        fragmentCreateBinding.lifecycleOwner = activity

        fragmentCreateBinding.include2.createViewModel = createViewModel

        createViewModel.wasEstateTypeClicked.observe(viewLifecycleOwner, {
                wasClicked -> if (wasClicked) {
            // Reset to FALSE to make sure no extra Alert is created
            alertDialog("Estate type", R.array.array_estate_type)
            createViewModel.wasEstateTypeClicked.value = false
        }
        })

        createViewModel.wasEstateEnergyScoreClicked.observe(viewLifecycleOwner, {
                wasClicked -> if (wasClicked) {
            // Reset to FALSE to make sure no extra Alert is created
            alertDialog("Energy score", R.array.array_energy_score)

            createViewModel.wasEstateEnergyScoreClicked.value = false
        }
        })

        return binding.root
    }

    private fun alertDialog(title: String, arrayList: Int){
        // Setup the alert builder
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)

        // Add a radio button list
        val checkedItem = 1 // cow
        builder.setSingleChoiceItems(arrayList, checkedItem) {
                dialog, which ->
            // CreateViewModel.estateType.value = resources.getStringArray(arrayList)[which].toString()
            // User checked an item
        }

        // Add OK and Cancel buttons
        builder.setPositiveButton("OK") { dialog, which ->
            // User clicked OK
        }
        builder.setNegativeButton("Cancel", null)

        // Create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }


}