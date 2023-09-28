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
import androidx.fragment.app.viewModels
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentCurrencyBinding
import com.oxymium.realestatemanager.viewmodel.CurrencyViewModel

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
    ): View {

        fragmentCurrencyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        fragmentCurrencyBinding.currencyViewModel = currencyViewModel
        fragmentCurrencyBinding.lifecycleOwner = activity

        // Listeners
        fragmentCurrencyBinding.convertRateInput.addTextChangedListener(
            object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) currencyViewModel.updateExchangeRate(s.toString().toDouble())
                else currencyViewModel.updateExchangeRate(0.00)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        fragmentCurrencyBinding.convertCurrencyInput.addTextChangedListener(
            object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) currencyViewModel.updateInputAmount(s.toString().toDouble())
                else currencyViewModel.updateInputAmount(0.00)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        return binding.root

    }


}