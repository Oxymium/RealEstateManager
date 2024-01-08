package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.TOOL_MENU_STEPS
import com.oxymium.realestatemanager.databinding.FragmentToolsBinding
import com.oxymium.realestatemanager.features.create.steps.MenuStepListener
import com.oxymium.realestatemanager.features.create.steps.MenuStepsAdapter
import com.oxymium.realestatemanager.viewmodel.ToolsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

// -------------
// ToolsFragment
// -------------

class ToolsFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentToolsBinding: FragmentToolsBinding
    private val binding get() = fragmentToolsBinding

    // RecyclerView
    private lateinit var menuStepsAdapter: MenuStepsAdapter

    // ViewModel
    private val toolsViewModel: ToolsViewModel by activityViewModel<ToolsViewModel>()

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

        observeToolStep()

        // RecyclerView setup
        val linearLayoutManager = LinearLayoutManager(requireActivity(), GridLayoutManager.HORIZONTAL, false)
        fragmentToolsBinding.navigatorBar.navigationToolsRecyclerView.layoutManager = linearLayoutManager

        // Observe list of steps
        toolsViewModel.toolMenuSteps.observe(viewLifecycleOwner) { toolMenuSteps ->
            menuStepsAdapter.submitList(toolMenuSteps)
        }

        // Setup adapter
        menuStepsAdapter = MenuStepsAdapter(
            // StepListener
            MenuStepListener {
                toolsViewModel.updateToolMenuStep(it)
            }
        )

        // RecyclerView adapter init
        fragmentToolsBinding.navigatorBar.navigationToolsRecyclerView.adapter = menuStepsAdapter

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        fragment.tag
        transaction?.replace(R.id.fragment_tools_container, fragment, "")
        transaction?.commit()
    }

    // Navigation handler for Tool process
    private fun observeToolStep() {
        lifecycleScope.launch {
            toolsViewModel.toolMenuStep.collect { toolMenuStep ->
                this@ToolsFragment.replaceFragment(
                    when (toolMenuStep.id) {
                        TOOL_MENU_STEPS[0].id -> CurrencyFragment()
                        TOOL_MENU_STEPS[1].id -> LoanFragment()
                        TOOL_MENU_STEPS[2].id -> DevFragment()
                        else -> CurrencyFragment()
                    }
                )
                // Update the current step to display its title in the Navigation
                toolsViewModel.updateCurrentToolMenuStep(toolMenuStep)

                // Update the list to flip the value of the selected item
                toolsViewModel.updateToolMenuSteps(
                    toolsViewModel.toolMenuSteps.value?.map {
                        it.copy(isSelected = it.id == toolMenuStep.id)
                    }
                )
            }
        }
    }

}