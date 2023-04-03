package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxymium.realestatemanager.R

// --------------
// ToolsViewModel
// --------------

class ToolsViewModel: ViewModel() {

    // -------------------
    // FRAGMENT NAVIGATION
    // -------------------
    val selectedTool: LiveData<Int> get() = _selectedTool
    private val _selectedTool: MutableLiveData<Int> = MutableLiveData<Int>()
    fun updateSelectedTool(value: Int){
        _selectedTool.value = when (value){
            1 -> R.id.currencyFragment
            2 -> R.id.loanFragment
            3 -> R.id.devFragment
            else -> 0
        }
    }

    val currentTool: LiveData<Int> get() = _currentTool
    private val _currentTool = MutableLiveData<Int>()
    fun updateCurrentTool(value: Int){
        _currentTool.value = value
    }

}