package com.oxymium.realestatemanager.features.tools

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentToolsBinding
import com.oxymium.realestatemanager.features.create.steps.StepListener
import com.oxymium.realestatemanager.features.create.steps.StepsAdapter
import com.oxymium.realestatemanager.viewmodel.ToolsViewModel
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
    private lateinit var stepsAdapter: StepsAdapter

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

        // toolsViewModel.updateCurrentTool()
        observeToolsSteps()

        // RecyclerView setup
        val linearLayoutManager = LinearLayoutManager(requireActivity(), GridLayoutManager.HORIZONTAL, false)
        fragmentToolsBinding.navigatorBar.navigationToolsRecyclerView.layoutManager = linearLayoutManager

        // Observe list of steps
        toolsViewModel.toolSteps.observe(viewLifecycleOwner){
            stepsAdapter.submitList(it?.toList())
            println(it)
        }

        // Setup adapter
        stepsAdapter = StepsAdapter(
            // StepListener
            StepListener {
                toolsViewModel.updateCurrentTool(it)
            }
        )

        toolsViewModel.currentTool.observe(viewLifecycleOwner) { step ->
            step?.let {
                toolsViewModel.updateToolSteps(toolsViewModel.toolSteps.value?.map { toolSteps ->
                    toolSteps.copy(isSelected = step.number == toolSteps.number)
                })
            }

        }

        // RecyclerView adapter init
        fragmentToolsBinding.navigatorBar.navigationToolsRecyclerView.adapter = stepsAdapter

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        fragment.tag
        transaction?.replace(R.id.fragment_tools_container, fragment, "")
        transaction?.commit()
    }

    // Navigation handler for Tools
    private fun observeToolsSteps(){
        toolsViewModel.currentTool.observe(viewLifecycleOwner) {
            this.replaceFragment( when (it.number) {
                null -> ToolsPlaceholderFragment()
                0 -> ToolsPlaceholderFragment()
                1 -> CurrencyFragment()
                2 -> LoanFragment()
                3 -> DevFragment()
                else -> ToolsPlaceholderFragment()
            })
            toolsViewModel.updateSelectedTool(it.number)
        }
    }
}