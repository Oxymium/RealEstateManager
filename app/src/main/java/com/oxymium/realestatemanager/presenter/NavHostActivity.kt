package com.oxymium.realestatemanager.presenter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.ActivityNavHostBinding
import com.oxymium.realestatemanager.model.EstateField
import com.oxymium.realestatemanager.model.databaseitems.Picture
import com.oxymium.realestatemanager.utils.Notifications
import com.oxymium.realestatemanager.viewmodel.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val NOTIFICATION_CHANNEL = "12300"

// -----------------------------------
// NavHostActivity (Launcher Activity)
// -----------------------------------

class NavHostActivity : AppCompatActivity() {

    private val activityTAG = javaClass.simpleName

    // DataBinding
    private lateinit var navHostActivityBinding: ActivityNavHostBinding
    private val binding get() = navHostActivityBinding

    // Perform tablet check
    private val isTablet get() = resources.getBoolean(R.bool.isTablet)

    // Hold picture's URI
    private var currentPhotoPath: String? = null

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    // ViewModels
    private val estateViewModel: EstateViewModel by viewModel()

    private val createViewModel: CreateViewModel by viewModel()

    private val devViewModel: DevViewModel by viewModel()

    private val mapSelectedViewModel: MapSelectedViewModel by viewModel()

    private val toolsViewModel: ToolsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navHostActivityBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_nav_host, findViewById(android.R.id.content), false)
        setContentView(binding.root)

        // Trace for debug purposes
        Log.d(activityTAG, "onCreate:")

        // Notifications
        createNotificationChannel()
        observeNotification()

        // Navigation
        initNavigationUI()
        observeShouldNavigateToDetailsFragment()
        observeShouldNavigateToEstatesFragment()
        observeWhenDetailsButtonIsClicked()

        observeEditedEstate()

        // Pictures
        observePictureActivityType()

        // Misc.
        hideSupportActionBar()
        disableWindowAutoResizingWhenKeyboardCalled()

    }

    private fun initNavigationUI() {
        estateViewModel.toggleIsTablet(isTablet)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottomNav)
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
    private fun observePictureActivityType() {
        createViewModel.pictureActivityType.observe(this) {
            if (it != 0) displayPictureDialogChoice(R.array.array_picture_selection)
        }
    }

    // CAMERA
    private fun startCameraApp() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue if the file was created
                photoFile?.also {
                    val pictureUri = FileProvider.getUriForFile(
                        this@NavHostActivity,
                        "com.oxymium.realestatemanager.fileprovider",
                        it
                    )
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
                    startCameraAppForResult.launch(intent)
                }
            }
        }
    }

    // CREATE PICTURE FILE
    @Throws(IOException::class)
    private fun createImageFile(): File{
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        ).apply{
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
        return image
    }

    // GALLERY
    private fun openGalleryApp() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //intent.type = "image/*"
            startGalleryAppForResult.launch(intent)
        }
    }

    // CAMERA CALLBACK
    private val startCameraAppForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK){
            when(createViewModel.pictureActivityType.value) {
                1 -> createViewModel.updateEstateField(EstateField.MainPicturePath(currentPhotoPath.toString()))
                2 -> createViewModel.addPictureToSecondaryPictures(Picture(currentPhotoPath.toString(), "Edit"))
            }
        }
    }

    // GALLERY CALLBACK
    private val startGalleryAppForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        // GALLERY
        if (it.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = it.data?.data
            val contentResolver = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            imageUri?.let { contentResolver.takePersistableUriPermission(imageUri, takeFlags) }
            when(createViewModel.pictureActivityType.value) {
                1 -> createViewModel.updateEstateField(EstateField.MainPicturePath(imageUri.toString()))
                2 -> createViewModel.addPictureToSecondaryPictures(
                    Picture(imageUri.toString(), "")
                )
            }
        }
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

    private fun observeShouldNavigateToDetailsFragment() {
        lifecycleScope.launch {
            estateViewModel.shouldNavigateToDetailsFragment.collect{ startDetailsFragment ->
                if (startDetailsFragment && !isTablet) {
                    navController.navigate(R.id.detailsFragment)
                }
            }
        }
    }

    // Trigger navigation if there's an Estate to edit
    private fun observeEditedEstate() {
        lifecycleScope.launch {
            estateViewModel.estateToEdit.collect { estateToEdit ->
                estateToEdit?.let {
                    navController.navigate(R.id.createEstateFragment)
                    // Provide an instance of the Estate to Edit
                    createViewModel.updateEstate(it)
                }
            }
        }
    }

    // Trigger navigation when Estate is done being Edited/Created
    private fun observeShouldNavigateToEstatesFragment() {
        lifecycleScope.launch {
            createViewModel.shouldNavigateToEstatesFragment.collect { navigateToDetailsFragment ->
                if (navigateToDetailsFragment) {
                    if (isTablet) navController.navigate(R.id.action_navigateFromCreateToEstatesDetailsFragment)
                    else navController.navigate(R.id.action_navigateFromCreateToDetailsFragment)
                    // Nullify edited Estate back to null
                    createViewModel.updateEstate(null)
                    estateViewModel.updateEstateToEdit(null)
                }
            }
        }
    }

    // Trigger navigation from Map to Details
    private fun observeWhenDetailsButtonIsClicked() {
        lifecycleScope.launch {
            mapSelectedViewModel.isDetailsButtonClicked.collect { isEditButtonClicked ->
                if (isEditButtonClicked) navController.navigate(R.id.detailsFragment)
                // Provide ID to selected Estate from Map
                estateViewModel.updateSelectedEstateId(
                    mapSelectedViewModel.selectedEstate.value?.id ?: 0L
                )

            }
        }
    }

    private fun displayPictureDialogChoice(arrayList: Int) {
        println(">>>> CALLED")
        // Alert Dialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Pictures from:")
        val checkedItem = -1
        var clickChoice: Int = -1
        builder.setSingleChoiceItems(arrayList, checkedItem) { _, which ->
            clickChoice = which
        }
        builder.setPositiveButton(R.string.alert_positive)
        { dialog, _ ->
            when (clickChoice) {
                0 -> {
                    println(">>>>> CAMERA CALLED")
                    startCameraApp()
                    dialog.cancel()
                }
                1 -> {
                    openGalleryApp()
                    dialog.cancel()
                }
            }}
        builder.setNegativeButton(R.string.alert_negative)
        { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    // --------------
    // NOTIFICATIONS
    // --------------

    // Observe CreateViewModel inserted Estate's ID to trigger a Notification with corresponding ID
    private fun observeNotification() {
        createViewModel.notificationId.observe(this) {
            Notifications(this, NOTIFICATION_CHANNEL).createNotification(it)
        }
    }

}
