package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentLoanBinding
import com.oxymium.realestatemanager.viewmodel.LoanViewModel

// ------------
// LaonFragment
// ------------

class LoanFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentLoanBinding: FragmentLoanBinding
    private val binding get() = fragmentLoanBinding

    // LoanViewModel
    private val loanViewModel: LoanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentLoanBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_loan, container, false)
        fragmentLoanBinding.loanViewModel = loanViewModel

        return binding.root
    }

}