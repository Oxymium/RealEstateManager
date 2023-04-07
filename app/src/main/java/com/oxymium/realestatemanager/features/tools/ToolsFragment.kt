package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentToolsBinding
import com.oxymium.realestatemanager.viewmodel.ToolsViewModel

// -------------
// ToolsFragment
// -------------

class ToolsFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentToolsBinding: FragmentToolsBinding
    private val binding get() = fragmentToolsBinding

    // ViewModel
    private val toolsViewModel: ToolsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentToolsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tools, container, false)
        fragmentToolsBinding.lifecycleOwner = activity
        binding.toolsViewModel = toolsViewModel
        binding.navigatorBar.toolsViewModel = toolsViewModel

        observeCreationSteps()

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        fragment.tag
        transaction?.replace(R.id.fragment_tools_container, fragment, "")
        transaction?.commit()
    }

    // Navigation handler for Creation process
    private fun observeCreationSteps(){
        val selectedTool = toolsViewModel.selectedTool.value
        toolsViewModel.currentTool.observe(viewLifecycleOwner) {
            if (it == selectedTool) return@observe
            this.replaceFragment(when (it) {
                1 -> CurrencyFragment()
                2 -> LoanFragment()
                3 -> DevFragment()
                else -> CurrencyFragment()
            })
            toolsViewModel.updateSelectedTool(it)
        }
    }

}