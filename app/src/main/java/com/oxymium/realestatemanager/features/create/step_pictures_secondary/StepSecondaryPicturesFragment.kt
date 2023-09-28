package com.oxymium.realestatemanager.features.create.step_pictures_secondary

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.SECONDARY_PICTURES_AMOUNT_LIMIT
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentStepSecondaryPicturesBinding
import com.oxymium.realestatemanager.features.create.CreateViewModel
import com.oxymium.realestatemanager.features.estates.CreatePictureAdapter
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.utils.PictureCommentListener
import com.oxymium.realestatemanager.utils.PictureDeleteListener
import com.oxymium.realestatemanager.utils.PictureListener
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory

// ----------------
// StepFourFragment
// ----------------

class StepSecondaryPicturesFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var stepSecondaryPicturesBinding: FragmentStepSecondaryPicturesBinding
    private val binding get() = stepSecondaryPicturesBinding

    private lateinit var pictureAdapter: CreatePictureAdapter

    private val createViewModel: CreateViewModel by activityViewModels {
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

        stepSecondaryPicturesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_secondary_pictures, container, false)
        stepSecondaryPicturesBinding.lifecycleOwner = activity
        stepSecondaryPicturesBinding.createViewModel = createViewModel

        // RecyclerView setup
        val linearLayoutManager = LinearLayoutManager(requireActivity(), GridLayoutManager.HORIZONTAL, false)
        stepSecondaryPicturesBinding.stepSecondaryPicturesRecyclerView.layoutManager = linearLayoutManager

        // Observe list of secondary Pictures
        createViewModel.secondaryPictures.observe(viewLifecycleOwner
        ) { it ->
            pictureAdapter.submitList(it?.toList())

            // Clear preview when list is emptied
            if (it != null) {
                if (it.isEmpty()) createViewModel.updateSecondaryPicturePreview(null)
            }

            // Limit number of secondary pictures
            it?.let {
                stepSecondaryPicturesBinding.stepSecondaryPicturesAddButton.visibility = when (it.size == SECONDARY_PICTURES_AMOUNT_LIMIT){
                    true -> View.GONE
                    else -> View.VISIBLE
                }
            }
        }

        // Setup adapter
        pictureAdapter = CreatePictureAdapter(
            // PictureListener
            PictureListener {
                createViewModel.updateSecondaryPicturePreview(it.path)
            },
            // Listeners to Delete Button
            PictureDeleteListener {
                Log.d("picture DELETE Clicked:", it.toString())
                // Delete clicked picture
                createViewModel.deletePictureFromSecondaryList(it)
            },
            // Listeners to Comment Button
            PictureCommentListener {
                val oldPicture = it
                val newPicture = it.copy()
                alertDialogSecondaryPictures(oldPicture, newPicture)
            })

        // RecyclerView adapter init
        stepSecondaryPicturesBinding.stepSecondaryPicturesRecyclerView.adapter = pictureAdapter

        // Load selected Secondary picture into Preview
        createViewModel.secondaryPicturePreview.observe(viewLifecycleOwner) {
            println(it)
            it?.let {
                Glide
                    .with(this@StepSecondaryPicturesFragment)
                    .load(it.toUri())
                    .error(R.color.space_cadet)
                    .placeholder(R.color.space_cadet)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(stepSecondaryPicturesBinding.stepSecondaryPicturesPreview)
            }
        }

        return binding.root
    }

    // -------------
    // ALERT DIALOG
    // -------------

    private fun alertDialogSecondaryPictures(oldPicture: Picture, newPicture: Picture){

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("(Re)name picture")

        // Set up the input
        val input = EditText(requireActivity())
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        // Set up the buttons
        builder.setPositiveButton(
            R.string.alert_positive
        ) { _, _ ->
            val newComment = input.text.toString()
            // Update comment
            newPicture.comment = newComment
            also{
                createViewModel.updateCommentFromSecondaryPictures(oldPicture, newPicture)} }
        builder.setNegativeButton(
            R.string.alert_negative
        ) { dialog, _ -> dialog.cancel() }

        builder.show()

    }
}
