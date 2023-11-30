package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.TOOLS_STEPS
import com.oxymium.realestatemanager.model.Step

// --------------
// ToolsViewModel
// --------------

class ToolsViewModel: ViewModel() {

    // -------------------
    // FRAGMENT NAVIGATION
    // -------------------
    val selectedTool: LiveData<Int?> get() = _selectedTool
    private val _selectedTool: MutableLiveData<Int?> = MutableLiveData<Int?>(null)
    fun updateSelectedTool(value: Int){
        _selectedTool.value = when (value){
            1 -> R.id.currencyFragment
            2 -> R.id.loanFragment
            3 -> R.id.devFragment
            else -> 0
        }
    }

    val currentTool: LiveData<Step> get() = _currentTool
    private val _currentTool = MutableLiveData<Step>()
    fun updateCurrentTool(step: Step){
        _currentTool.value = step
    }

    val toolSteps: LiveData<List<Step>?> get() = _toolSteps
    private val _toolSteps = MutableLiveData(TOOLS_STEPS)
    fun updateToolSteps(toolSteps: List<Step>?){
        _toolSteps.value = toolSteps
    }

}