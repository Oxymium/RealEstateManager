package com.oxymium.realestatemanager.features.estates

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding2.widget.textChanges
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentEstatesBinding
import com.oxymium.realestatemanager.utils.EstateListener
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit


// ---------------
// EstatesFragment
// ---------------

class EstatesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentEstatesBinding: FragmentEstatesBinding
    private val binding get() = fragmentEstatesBinding

    // EstateViewModel
    private val estateViewModel: EstateViewModel by activityViewModels() {
        EstateViewModelFactory((activity?.application as EstatesApplication).repository)
    }

    // RecyclerView Adapter
    private lateinit var estateAdapter: EstateAdapter

    // RX
    private val disposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentEstatesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_estates, container, false)

        fragmentEstatesBinding.lifecycleOwner = activity
        val gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        fragmentEstatesBinding.fragmentEstatesRecyclerView.layoutManager = gridLayoutManager

        // Observe list
        estateViewModel.estateListLiveData.observe(viewLifecycleOwner,
            { estateAdapter.submitList(it)
            })

        estateViewModel.setQuickSearchQuery("")

        // Setup adapter
        estateAdapter = EstateAdapter(EstateListener { estate ->
            // Send selected Estate to ViewModel
            estateViewModel.selectedEstate.postValue(estate)
            Log.d("estate Clicked:", estate.id.toString())
        })

        // (Quick Search) Text listener
        fragmentEstatesBinding.fragmentEstatesQuickSearchInputEdit.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // OnChange -> update Query
                s?.let { estateViewModel.setQuickSearchQuery(it.toString())}
            }
        })

        // Adapter init
        fragmentEstatesBinding.fragmentEstatesRecyclerView.adapter = estateAdapter

        return binding.root
    }

}