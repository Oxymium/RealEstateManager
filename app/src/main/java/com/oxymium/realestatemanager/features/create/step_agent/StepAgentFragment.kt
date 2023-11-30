package com.oxymium.realestatemanager.features.create.step_agent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentStepAgentsBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.features.create.steps.RecyclerViewScrollListener
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.viewmodel.factories.CreateViewModelFactory

class StepAgentFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepAgentBinding: FragmentStepAgentsBinding
    private val binding get() = stepAgentBinding

    // RecyclerView Adapter
    private lateinit var agentAdapter: AgentAdapter

    private val createViewModel: CreateViewModel by activityViewModels {
        CreateViewModelFactory(
            (activity?.application as EstatesApplication).agentRepository,
            (activity?.application as EstatesApplication).estateRepository,
            (activity?.application as EstatesApplication).pictureRepository
        )
    }

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

        // Initial list from DB
        createViewModel.agentsFromDatabase.observe(viewLifecycleOwner){ createViewModel.updateAgents(it) }

        // Agents
        agentAdapter = AgentAdapter(
            AgentListener {
                // UPDATE Estate with Agent ID
                createViewModel.updateEstateField(EstateField.AgentId(it.id))
            }
        )

        createViewModel.agents.observe(viewLifecycleOwner){ agentAdapter.submitList(it) }

        createViewModel.estate.observe(viewLifecycleOwner){
                estate ->
            // When an Agent's ID is available (ie. an agent was clicked)
            if (estate?.agent_id == null) {
                createViewModel.updateAgents(createViewModel.agents.value?.map{agent ->
                    agent.copy(isSelected = false)
                })
            }else{
                createViewModel.updateAgents(createViewModel.agents.value?.map{agent ->
                    agent.copy(isSelected = agent.id == estate.agent_id)
                })
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