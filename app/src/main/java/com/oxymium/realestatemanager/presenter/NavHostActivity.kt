package com.oxymium.realestatemanager.presenter

import android.Manifest
import android.R.attr
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.database.EstatesApplication
import com.oxymium.realestatemanager.model.Picture
import com.oxymium.realestatemanager.viewmodel.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.data
import androidx.navigation.NavController
import com.oxymium.realestatemanager.utils.Notifications


// -----------------------------------
// NavHostActivity (Launcher Activity)
// -----------------------------------

class NavHostActivity : AppCompatActivity() {

    // Tablet detection
    var isTablet: Boolean = false

    private val NOTIFICATION_CHANNEL = "12300"
    //////
    var photoFile: File? = null
    val CAPTURE_IMAGE_REQUEST = 222
    val OPEN_IMAGE_REQUEST = 333
    var mCurrentPhotoPath: String? = null
    //////////

    // Provide inserted estate's ID for Notifications
    var returnedEstateId: Long = -1

    private val activityTAG = javaClass.simpleName

    val navController get() = Navigation.findNavController(this, R.id.navHostFragment)

    // Tablet (if)
    // val navController2 get() = Navigation.findNavController(this, R.id.navHostFragment2)

    private lateinit var bottomNavigationView: BottomNavigationView


    // ViewModel injection
    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory((application as EstatesApplication).repository, ((application as EstatesApplication).repository2))
    }

    private val createViewModel: CreateViewModel by viewModels {
        CreateViewModelFactory((application as EstatesApplication).repository, ((application as EstatesApplication).repository2))
    }

    private val toolsViewModel: ToolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)

        // Trace for debug purposes
        Log.d(activityTAG, "onCreate:")

        initNavigationUI()

        createNotificationChannel()

        observePictureDispatchIntent()
        //observeSelectedEstate()
        observeToolsSelectedCategory()
        observeInsertedEstateId()
        observeNotificationTrigger()
        observeNavigateToEstatesFragment()
        observeNavigateToDetailsFragment()
        observeEditMode()

        hideSupportActionBar()
        disableWindowAutoResizingWhenKeyboardCalled()

        // Check if device is tablet
        isTablet = resources.getBoolean(R.bool.isTablet)
        if (isTablet) {
            Log.d(activityTAG, "device is Tablet")
            estateViewModel.isTablet.value = true
        } else {
            Log.d(activityTAG, "device isn't Tablet")
        }

    }

    // Navigate from EstatesFragment -> DetailsFragment onClick
    private fun observeSelectedEstate() {
        estateViewModel.selectedEstate.observe(this, { estate ->
            if (estate != null) {
                navController.navigate(R.id.action_estatesFragment_to_detailsFragment)
            }
        })
    }

    // For Tools fragment
    private fun observeToolsSelectedCategory() {
        toolsViewModel.categoryClicked.observe(this, { categoryValue ->
            when (categoryValue) {
                1 -> navController.navigate(R.id.action_toolsFragment_to_currencyFragment)
                2 -> navController.navigate(R.id.action_toolsFragment_to_loanFragment)
                3 -> navController.navigate(R.id.action_toolsFragment_to_devFragment)
            }
        })
    }

    private fun initNavigationUI() {
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    private fun hideSupportActionBar() {
        supportActionBar?.hide()
    }

    // Prevents window resizing when virtual keyboard opens
    private fun disableWindowAutoResizingWhenKeyboardCalled() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    // Pictures
    private fun observePictureDispatchIntent() {
        createViewModel.triggerPictureActivityFrom.observe(this, { dispatchIntent ->
            if (dispatchIntent != 0) {
            displayPictureDialogChoice(R.array.array_picture_selection, dispatchIntent)
            }
        })
    }

    ////////
    private fun openCamera(from: Int) {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                try {
                    photoFile = createImageFile()
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        val photoURI = FileProvider.getUriForFile(
                            this,
                            "com.oxymium.realestatemanager.fileprovider",
                            photoFile!!
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST)
                    }
                } catch (ex: Exception) {
                    // Error occurred while creating the File
                    println("Error occurred while creating the file")
                }

            } else {
                println("Another error")
            }
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // CAMERA
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val myBitmap = BitmapFactory.decodeFile(photoFile!!.absolutePath)
            // Send picturePath to CreateViewModel
            // 1 MainPicture || 2 Secondary Picture
            val from = createViewModel.triggerPictureActivityFrom.value
            if (from == 1){
                createViewModel.mainPicturePath.postValue(mCurrentPhotoPath)
            }else if(from == 2){
                //Create new secondary picture
                val newSecondaryPicture = Picture(mCurrentPhotoPath!!, "")
                createViewModel.addPictureToSecondaryList(newSecondaryPicture)
            }}
        // GALLERY
        if(requestCode == OPEN_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            val selectedImageUri: Uri? = data!!.data
            val imagePath = selectedImageUri!!.path
            //val imageFile = File(imagePath!!)
            val from = createViewModel.triggerPictureActivityFrom.value
            if (from == 1){
                createViewModel.mainPicturePath.postValue(selectedImageUri.toString())
            }else if(from == 2){
                //Create new secondary picture
                val newSecondaryPicture = Picture(imagePath!!, "")
                createViewModel.addPictureToSecondaryList(newSecondaryPicture)
            }

        }else{
            println("Request cancelled or something went wrong.")
        }
    }

    // GALLERY
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, OPEN_IMAGE_REQUEST)
    }

    // Open notification channel
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Real Estate Notifications"
            val descriptionText = "Inserted Estates"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Observe ViewModel to trigger Notification
    private fun observeNotificationTrigger() {
        createViewModel.triggerNotification.observe(this, {
                triggerNotifications ->

            if (triggerNotifications) {

                Notifications(this, NOTIFICATION_CHANNEL).createNotification(returnedEstateId)
                // Reset Boolean to trigger new notification
                createViewModel.triggerNotification.postValue(false)
            }
        })
    }

    // Observe returned Estate ID to provide to Notification message
    private fun observeInsertedEstateId() {
        createViewModel.returnedEstateIdValue.observe(this, { estateIdValue -> returnedEstateId = estateIdValue
        })
    }

    // Observe returned Estate ID to provide to Notification message
    private fun observeNavigateToEstatesFragment() {
        createViewModel.navigateToEstatesFragment.observe(this, { navigateToEstatesFragment ->
            if (navigateToEstatesFragment) {
                navController.navigate(R.id.action_createFragment_to_estatesFragment)
                // Reset value after use
            }
        })
    }

    private fun observeNavigateToDetailsFragment() {
        estateViewModel.startDetailsFragmentFrom.observe(this, { navigateToDetailsFragment ->
            if (navigateToDetailsFragment == 1) {
                if (!isTablet){
                    navController.navigate(R.id.action_estatesFragment_to_detailsFragment) }
            }else if (navigateToDetailsFragment == 2){
                navController.navigate(R.id.action_mapFragment_to_detailsFragment) // Reset value after use
            }
        })
    }

    private fun observeEditMode() {
        estateViewModel.estateToEdit.observe(this, {
                estateToEdit ->
            createViewModel.editedEstate.postValue(estateToEdit)
            navController.navigate(R.id.action_detailsFragment_to_createFragment)
        })
    }

    private fun displayPictureDialogChoice(arrayList: Int, dispatchIntent: Int) {
        // Alert Dialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Pictures from:")
        val checkedItem = 0
        var clickChoice: Int = -1
        builder.setSingleChoiceItems(arrayList, checkedItem) { dialog, which ->
            clickChoice = which
        }
        builder.setPositiveButton("Go")
        { dialog, which ->
            when (clickChoice) {
                0 -> {
                    openCamera(dispatchIntent)
                    dialog.cancel()
                }
                1 -> {
                    openGallery()
                    dialog.cancel()
                }
        }}
        builder.setNegativeButton("Cancel")
        { dialog, which -> dialog.cancel() }

        builder.show()
    }
}
