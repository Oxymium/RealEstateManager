package com.oxymium.realestatemanager.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieEntry
import com.oxymium.realestatemanager.model.Loan

// -------------
// LaonViewModel
// -------------

class LoanViewModel: ViewModel() {

    val loan = MutableLiveData<Loan>(Loan(0f, 0f, 20, 1.5f))

    fun updateBorrowedAmount(borrowedAmount: Float){
        loan.value?.borrowedAmount = borrowedAmount
        loan.value = loan.value
    }

    fun updateDeposit(deposit: Float){
        loan.value?.deposit = deposit
        loan.value = loan.value
    }

    fun updateDuration(duration: Int){
        loan.value?.duration = duration
        loan.value = loan.value
    }

    fun updateInterestRate(interestRate: Float){
        loan.value?.interestRate = interestRate
        loan.value = loan.value
    }

}


