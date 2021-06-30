package com.oxymium.realestatemanager.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxymium.realestatemanager.model.Estate

// --------------
// ToolsViewModel
// --------------

class ToolsViewModel: ViewModel() {

    private val viewModelTAG = javaClass.simpleName

    // DEFAULT: 0
    var categoryClicked: MutableLiveData<Int> = MutableLiveData(0)

    // CURRENCY CATEGORY = 1
    fun onClickCurrencyCategory(){
        Log.d(viewModelTAG, "onClickCurrencyCategory: ")
        categoryClicked.postValue(1)
    }

    // LOAN CATEGORY = 2
    fun onClickLoanCategory(){
        Log.d(viewModelTAG, "onClickCurrencyCategory: ")
        categoryClicked.postValue(2)
    }

    // DEV CATEGORY = 3
    fun onClickDevCategory(){
        Log.d(viewModelTAG, "onClickDevCategory: ")
        categoryClicked.postValue(3)
    }

}