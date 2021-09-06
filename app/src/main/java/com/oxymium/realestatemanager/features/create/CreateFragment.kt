package com.oxymium.realestatemanager.features.create

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.databinding.FragmentCreateBinding
import com.oxymium.realestatemanager.features.estates.CreatePictureAdapter
import com.oxymium.realestatemanager.utils.PictureCommentListener
import com.oxymium.realestatemanager.utils.PictureDeleteListener
import com.oxymium.realestatemanager.viewmodel.CreateViewModel
import com.oxymium.realestatemanager.viewmodel.CreateViewModelFactory
import android.text.InputType
import android.text.TextWatcher

import android.widget.EditText
import com.oxymium.realestatemanager.model.Picture


// --------------
// CreateFragment
// --------------

class CreateFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentCreateBinding: FragmentCreateBinding
    private val binding get() = fragmentCreateBinding

    // RecyclerView Adapter
    private lateinit var pictureAdapter: CreatePictureAdapter

    private val createViewModel: CreateViewModel by activityViewModels() {
        CreateViewModelFactory(
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
    ): View? {

        fragmentCreateBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)
        fragmentCreateBinding.lifecycleOwner = activity

        // Bind all layouts
        fragmentCreateBinding.include1.createViewModel = createViewModel
        fragmentCreateBinding.include2.createViewModel = createViewModel
        fragmentCreateBinding.include3.createViewModel = createViewModel
        fragmentCreateBinding.include4.createViewModel = createViewModel
        fragmentCreateBinding.include5.createViewModel = createViewModel
        fragmentCreateBinding.include6.createViewModel = createViewModel
        fragmentCreateBinding.include7.createViewModel = createViewModel
        fragmentCreateBinding.include8.createViewModel = createViewModel
        fragmentCreateBinding.include9.createViewModel = createViewModel

        // EDIT MODE
        createViewModel.editedEstate.observe(viewLifecycleOwner, { editedEstate ->
            createViewModel.type.value = editedEstate.type
            createViewModel.energyScore.value = editedEstate.energy
            createViewModel.price.value = editedEstate.price
            createViewModel.surface.value = editedEstate.surface
            createViewModel.rooms.value = editedEstate.rooms
            createViewModel.bedrooms.value = editedEstate.bedrooms
            createViewModel.bathrooms.value = editedEstate.bathrooms
            createViewModel.address.value = editedEstate.address
            createViewModel.zipCode.value = editedEstate.zipCode
            createViewModel.location.value = editedEstate.location
            createViewModel.description.value = editedEstate.description
            createViewModel.nearbyPlaces.value = editedEstate.nearbyPlaces
            createViewModel.agent.value = editedEstate.agent
        })


        // Type
        createViewModel.wasEstateTypeClicked.observe(viewLifecycleOwner, { wasClicked ->
            if (wasClicked) {
                // Reset to FALSE to make sure no extra Alert is created
                alertDialog("Estate type", R.array.array_estate_type, 1)
                createViewModel.wasEstateTypeClicked.value = false
            }
        })

        // Energy score
        createViewModel.wasEstateEnergyScoreClicked.observe(viewLifecycleOwner, { wasClicked ->
            if (wasClicked) {
                // Reset to FALSE to make sure no extra Alert is created
                alertDialog("Energy score", R.array.array_energy_score, 2)

                createViewModel.wasEstateEnergyScoreClicked.value = false
            }
        })

        // Agent name
        createViewModel.wasAgentNameClicked.observe(viewLifecycleOwner, { wasClicked ->
            if (wasClicked) {
                // Reset to FALSE to make sure no extra Alert is created
                alertDialog("Agent", R.array.array_agent_name, 3)

                createViewModel.wasEstateEnergyScoreClicked.value = false
            }
        })

        // Price
        fragmentCreateBinding.include3.layoutCreatePriceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.price.postValue(s.toString().toInt())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // Surface
        fragmentCreateBinding.include3.layoutCreateSurfaceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.surface.postValue(s.toString().toInt())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // Rooms
        fragmentCreateBinding.include3.layoutCreateRoomsInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.rooms.postValue(s.toString().toInt())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // Bedrooms
        fragmentCreateBinding.include3.layoutCreateSurfaceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.bedrooms.postValue(s.toString().toInt())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // Bathrooms
        fragmentCreateBinding.include3.layoutCreateSurfaceInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.bathrooms.postValue(s.toString().toInt())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // Address
        fragmentCreateBinding.include4.layoutCreateStreetInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.address.postValue(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // ZipCode
        fragmentCreateBinding.include4.layoutCreateZipCodeInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.zipCode.postValue(s.toString().toInt())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // Location
        fragmentCreateBinding.include4.layoutCreateLocationInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.location.postValue(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // High-speed internet
        fragmentCreateBinding.include5.layoutCreateMiscellaneousHighSpeedInternet.setOnCheckedChangeListener {
                checkBox, isChecked ->
            if (isChecked) {
                createViewModel.highSpeedInternet.postValue(true)
            }else{
                createViewModel.highSpeedInternet.postValue(false)
            }
        }


        // Description
        fragmentCreateBinding.include5.layoutCreateMiscellaneousDescriptionInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.description.postValue(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        // Description
        fragmentCreateBinding.include5.layoutCreateMiscellaneousNearbyInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) createViewModel.nearbyPlaces.postValue(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })



        // Observe Main Picture and load into ImageView with Glide library
        createViewModel.mainPicturePath.observe(viewLifecycleOwner, { picturePath ->
            Glide.with(this@CreateFragment).load(picturePath).into(fragmentCreateBinding.include6.layoutCreateMainPictureMainPictureView)
        })

        // RecyclerView setup
        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 3, GridLayoutManager.HORIZONTAL, false)
        fragmentCreateBinding.include7.layoutCreateSecondaryPicturesRecyclerView.layoutManager =
            gridLayoutManager

        // Observe list of secondary Pictures
        createViewModel.listSecondaryPictures.observe(viewLifecycleOwner,
            {
                pictureAdapter.submitList(it.toList())
            })

        // Setup adapter
        pictureAdapter = CreatePictureAdapter(
            // Listeners to Delete Button
            PictureDeleteListener { secondaryPicture ->
                Log.d("picture DELETE Clicked:", secondaryPicture.toString())
                // Delete clicked picture
                createViewModel.deletePictureFromSecondaryList(secondaryPicture)
                // Send selected Estate to ViewModel
            },
            // Listeners to Comment Button
            PictureCommentListener { secondaryPicture ->
                Log.d("pictur COMMENT Clicked:", secondaryPicture.toString())
                alertDialogPicture2(secondaryPicture)
            })

        // RecyclerView adapter init
        fragmentCreateBinding.include7.layoutCreateSecondaryPicturesRecyclerView.adapter =
            pictureAdapter


        // Save button
        createViewModel.triggerSaveAlertDialog.observe(viewLifecycleOwner,
            { trigger ->
                if (trigger) {
                    alertDialogTesst()
                }
            })

        return binding.root
    }

    private fun alertDialog(title: String, arrayList: Int, valueToUpdate: Int) {
        // Setup the alert builder
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)

        // Add a radio button list
        val checkedItem = 0
        builder.setSingleChoiceItems(arrayList, checkedItem) { dialog, which ->
            if (valueToUpdate == 1) {
                createViewModel.type.postValue(resources.getStringArray(arrayList)[which].toString())
            }
            if (valueToUpdate == 2) {
                createViewModel.energyScore.postValue(resources.getStringArray(arrayList)[which].toString())
            } else if (valueToUpdate == 3) {
                createViewModel.agent.postValue(resources.getStringArray(arrayList)[which].toString())
            }
        }
        // Add OK and Cancel buttons
        builder.setPositiveButton("OK") { dialog, which ->
        }

        builder.setNegativeButton("Cancel", null)

        // Create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun alertDialogTesst() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder!!.setMessage("Do you want to save this Estate?")
            .setTitle("Save")
        builder.apply {
            setPositiveButton("Yes") { dialog, id ->
                createViewModel.insertEstateIntoDatabase()
            }
            setNegativeButton("No") { dialog, id ->
                val selectedId = id
            }
        }
        val dialog: AlertDialog? = builder.create()

        dialog!!.show()
    }

    private fun alertDialogPicture2(picture: Picture){

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Title")

        // Set up the input

        // Set up the input
        val input = EditText(requireActivity())
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        // Set up the buttons
        // Set up the buttons
        builder.setPositiveButton(
            "OK"
        ) { dialog, which ->
            val newComment = input.text.toString()
            // Update comment
            picture.comment = newComment
            createViewModel.updatePictureCommentFromSecondaryList(picture) }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }

        builder.show()

    }

}