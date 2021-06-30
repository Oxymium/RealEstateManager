package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentCurrencyBinding
import com.oxymium.realestatemanager.databinding.FragmentSearchBinding
import com.oxymium.realestatemanager.viewmodel.CurrencyViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModel
import com.oxymium.realestatemanager.viewmodel.EstateViewModelFactory
import com.oxymium.realestatemanager.viewmodel.ToolsViewModel

// ----------------
// CurrencyFragment
// ----------------

class CurrencyFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentCurrencyBinding: FragmentCurrencyBinding
    private val binding get() = fragmentCurrencyBinding

    // CurrencyViewModel
    private val currencyViewModel: CurrencyViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentCurrencyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        fragmentCurrencyBinding.currencyViewModel = currencyViewModel

        return binding.root
    }

}