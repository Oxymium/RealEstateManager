package com.oxymium.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.realestatemanager.TOOL_MENU_STEPS
import com.oxymium.realestatemanager.model.MenuStep
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

// --------------
// ToolsViewModel
// --------------

class ToolsViewModel: ViewModel() {

    // -------------------
    // FRAGMENT NAVIGATION
    // -------------------

    val currentToolMenuStep: LiveData<MenuStep?> get() = _currentToolMenuStep
    private val _currentToolMenuStep = MutableLiveData<MenuStep?>(null)
    fun updateCurrentToolMenuStep(toolMenuStep: MenuStep) {
        _currentToolMenuStep.value = toolMenuStep
    }
    val toolMenuSteps: LiveData<List<MenuStep>?> get() = _toolMenuSteps
    private val _toolMenuSteps = MutableLiveData(TOOL_MENU_STEPS)
    fun updateToolMenuSteps(toolMenuSteps: List<MenuStep>?) {
        _toolMenuSteps.value = toolMenuSteps
    }

    val toolMenuStep: SharedFlow<MenuStep> get() = _toolMenuStep
    private val _toolMenuStep = MutableSharedFlow<MenuStep>(replay = 0)
    fun updateToolMenuStep(toolMenuStep: MenuStep) {
        viewModelScope.launch {
            _toolMenuStep.emit(toolMenuStep)
        }
    }


}