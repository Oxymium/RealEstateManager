package com.oxymium.realestatemanager.features.create.step_agent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentStepAgentsBinding
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.viewmodel.CreateViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StepAgentFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepAgentBinding: FragmentStepAgentsBinding
    private val binding get() = stepAgentBinding

    // RecyclerView Adapter
    private lateinit var agentAdapter: AgentAdapter

    private val createViewModel: CreateViewModel by activityViewModel<CreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        stepAgentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_agents, container, false)
        stepAgentBinding.lifecycleOwner = activity
        stepAgentBinding.createViewModel = createViewModel

        // -----
        // AGENT
        // -----

        val gridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        stepAgentBinding.stepOneAgentRecyclerView.layoutManager = gridLayoutManager

        agentAdapter = AgentAdapter(
            AgentListener { agent ->
                agent.id?.let { createViewModel.updateSelectedAgent(it) }
            }
        )

        // Types
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createViewModel.combinedAgents.collect {
                    agentAdapter.submitList(it)
                }
            }
        }

        // Adapter init
        stepAgentBinding.stepOneAgentRecyclerView.adapter = agentAdapter

        // RecyclerView side reached
        val onScrollToTop: () -> Unit = {
            createViewModel.updateReachedAgentSide(ReachedSide.TopSide)
        }

        val onScrollToBottom: () -> Unit = {
            createViewModel.updateReachedAgentSide(ReachedSide.BottomSide)
        }

        val scrollListener = RecyclerViewScrollListener(null, null, onScrollToTop, onScrollToBottom)
        stepAgentBinding.stepOneAgentRecyclerView.addOnScrollListener(scrollListener)

        return binding.root
    }
}