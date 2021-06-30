package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// -------------
// LaonViewModel
// -------------

class LoanViewModel: ViewModel() {

    // Loan amount
    var borrowedAmount: MutableLiveData<String> = MutableLiveData("")
    var deposit: MutableLiveData<String> = MutableLiveData("")
    var duration: MutableLiveData<String> = MutableLiveData("")
    var interestRate: MutableLiveData<String> = MutableLiveData("")

}