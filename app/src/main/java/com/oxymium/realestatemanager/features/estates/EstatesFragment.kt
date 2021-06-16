package com.oxymium.realestatemanager.features.estates

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentEstatesBinding
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.utils.EstateListener
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import com.oxymium.realestatemanager.viewmodel.Injection
import java.util.*

// ---------------
// EstatesFragment
// ---------------

class EstatesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentEstatesBinding: FragmentEstatesBinding
    private val binding get() = fragmentEstatesBinding

    // EstateViewModel
    private lateinit var estateViewModelFactory: EstateViewModelFactory
    private lateinit var estateViewModel: EstateViewModel

    // RecyclerView Adapter
    private lateinit var estateAdapter: EstateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

        // EstateViewModel init with Factory
        estateViewModel = activity?.run {
            estateViewModelFactory = Injection.provideEstateViewModelFactory(context)
            ViewModelProvider(this@EstatesFragment.requireActivity())[EstateViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentEstatesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_estates, container, false)

        fragmentEstatesBinding.lifecycleOwner = activity
        //fragmentEstatesBinding.estateViewModel = estateViewModel

        val gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        fragmentEstatesBinding.fragmentEstatesRecyclerView.layoutManager = gridLayoutManager

        setupEstateAdapter(
            requireContext(),
            observeEstateList(),
            EstateListener { estate ->
                // Send selected Estate to ViewModel
                estateViewModel.selectedEstate.postValue(estate)
                Log.d("estate Clicked:", estate.id.toString())
            }
        )

        fragmentEstatesBinding.fragmentEstatesRecyclerView.adapter = estateAdapter

        return binding.root

    }

    private fun setupEstateAdapter(context: Context, estates: List<Estate>, listener:EstateListener){

        estateAdapter = EstateAdapter(context, estates, listener)

    }

    // Provide adapter with Estates from ViewModel
    private fun observeEstateList(): List<Estate>{

        var listEstate = mutableListOf<Estate>()

        estateViewModel.estates.observe(viewLifecycleOwner){
                estates ->
            if (estates != null){
                estateAdapter.updateData(estates)
                estateAdapter.notifyDataSetChanged()
                listEstate = Collections.unmodifiableList(estates)
            }else{
                Toast.makeText(context, "empty", Toast.LENGTH_LONG).show()
            }
        }

        return listEstate

    }

}