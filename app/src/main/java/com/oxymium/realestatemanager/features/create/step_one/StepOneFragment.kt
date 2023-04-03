package com.oxymium.realestatemanager.features.create.step_one

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
import com.oxymium.realestatemanager.databinding.FragmentStepOneBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.utils.AgentListener
import com.oxymium.realestatemanager.utils.LabelListener
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

// ---------------
// StepOneFragment
// ---------------

class StepOneFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepOneBinding: FragmentStepOneBinding
    private val binding get() = stepOneBinding

    // RecyclerView Adapter
    private lateinit var agentAdapter: AgentAdapter
    private lateinit var labelAdapter: LabelAdapter

    private val createViewModel: CreateViewModel by activityViewModels() {
        CreateViewModelFactory(
            (activity?.application as EstatesApplication).repository3,
            (activity?.application as EstatesApplication).repository,
            (activity?.application as EstatesApplication).repository2
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

        stepOneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_one, container, false)
        stepOneBinding.lifecycleOwner = activity
        stepOneBinding.createViewModel = createViewModel


        // -----
        // AGENT
        // -----

        val gridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        stepOneBinding.stepOneAgentRecyclerView.layoutManager = gridLayoutManager

        // Initial list from DB
        createViewModel.agentsFromDatabase.observe(viewLifecycleOwner){ createViewModel.updateAgents(it) }

        // Agents
        agentAdapter = AgentAdapter(
            AgentListener {
                createViewModel.updateSelectedAgentId(it.id)
            }
        )

        createViewModel.agents.observe(viewLifecycleOwner){ agentAdapter.submitList(it) }

        createViewModel.selectedAgentId.observe(viewLifecycleOwner){
            selectedId ->
            // When an Agent's ID is available (ie. an agent was clicked)
            if (selectedId == null) {
                createViewModel.updateAgents(createViewModel.agents.value?.map{agent ->
                    agent.copy(isSelected = false)
                })
            }else{
                createViewModel.updateAgents(createViewModel.agents.value?.map{agent ->
                    agent.copy(isSelected = agent.id == selectedId)
                })
            }
        }

        // Adapter init
        stepOneBinding.stepOneAgentRecyclerView.adapter = agentAdapter


        // -----
        // TYPE
        // -----

        val gridLayoutManager2 = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        stepOneBinding.stepOneTypeRecyclerView.layoutManager = gridLayoutManager2

        labelAdapter = LabelAdapter(
            LabelListener {
                createViewModel.updateSelectedType(it.label)
            }
        )

        // Types
        createViewModel.types.observe(viewLifecycleOwner){ labelAdapter.submitList(it) }

        createViewModel.selectedType.observe(viewLifecycleOwner){
                selectedType ->

            if (selectedType == null) {
                createViewModel.updateTypes(createViewModel.types.value?.map{
                        type ->
                    type.copy(isSelected = false)
                })
            }else{
                createViewModel.updateTypes(createViewModel.types.value?.map{
                        type ->
                    type.copy(isSelected = type.label == selectedType)
                })
            }
        }


        // Adapter init
        stepOneBinding.stepOneTypeRecyclerView.adapter = labelAdapter

        return binding.root
    }

}