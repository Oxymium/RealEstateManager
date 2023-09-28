package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.oxymium.realestatemanager.model.Loan

// -------------
// LaonViewModel
// -------------

class LoanViewModel: ViewModel() {

    val loan: LiveData<Loan> get() = _loan
    private val _loan = MutableLiveData(
        Loan(
            0f,
            0f,
            20,
            1.5f)
    )
    private fun updateLoan(loan: Loan){
        _loan.value = loan
    }
    fun updateBorrowedAmount(borrowedAmount: Float){
        val currentLoan = loan.value ?: Loan()
        currentLoan.borrowedAmount = borrowedAmount
        updateLoan(currentLoan)
    }

    fun updateDeposit(deposit: Float){
        val currentLoan = loan.value ?: Loan()
        currentLoan.deposit = deposit
        updateLoan(currentLoan)
    }

    fun updateDuration(duration: Int){
        val currentLoan = loan.value ?: Loan()
        currentLoan.duration = duration
        updateLoan(currentLoan)
    }

    fun updateInterestRate(interestRate: Float){
        val currentLoan = loan.value ?: Loan()
        currentLoan.interestRate = interestRate
        updateLoan(currentLoan)
    }

}


