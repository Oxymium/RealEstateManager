package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.oxymium.realestatemanager.model.Loan

// -------------
// LoanViewModel
// -------------

class LoanViewModel: ViewModel() {

    val loan: LiveData<Loan> get() = _loan
    private val _loan = MutableLiveData(
        Loan()
    )

    fun updateBorrowedAmount(borrowedAmount: Float) {
        _loan.value =
            loan.value?.copy(
                borrowedAmount = borrowedAmount
            )
    }

    fun updateDeposit(deposit: Float){
        _loan.value =
            loan.value?.copy(
                deposit = deposit
            )
    }

    fun updateDuration(duration: Int){
        _loan.value =
            loan.value?.copy(
                duration = duration
            )
    }

    fun updateInterestRate(interestRate: Float){
        _loan.value =
            loan.value?.copy(
                interestRate = interestRate
            )
    }

}


