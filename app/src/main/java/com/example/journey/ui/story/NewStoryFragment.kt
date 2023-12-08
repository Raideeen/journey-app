package com.example.journey.ui.story

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.journey.R
import com.example.journey.model.StoryEntity
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Fragment for creating a new story.
 * This fragment allows the user to input a title, subtitle, and select an image for a new story.
 * The new story is then saved to the database.
 */
class NewStoryFragment : Fragment(R.layout.fragment_new_story) {

    // UI elements
    private lateinit var titleEditText: EditText
    private lateinit var subtitleEditText: EditText
    private lateinit var selectImageButton: Button
    private lateinit var saveButton: Button

    private lateinit var previewTitle: TextView
    private lateinit var previewSubtitle: TextView
    private lateinit var previewImage: ImageView

    // Launcher for picking an image from the gallery
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    // ViewModel for handling story data
    private val storyViewModel: StoryViewModel by activityViewModels { StoryViewModel.Factory }

    // URI of the selected image
    private var selectedImageUri: Uri? = null

    // Launcher for requesting permission to read images from the media
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, open the gallery
            openImagePicker()
        } else {
            // Handle the denial of permission
            Snackbar.make(
                requireView(),
                "Permission denied",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Opens the image picker to select an image from the gallery.
     */
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    /**
     * Called when the fragment is created.
     * Registers a callback for picking an image from the gallery.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register a callback for picking an image from the gallery
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                selectedImageUri = result.data?.data
                previewImage.setImageURI(selectedImageUri)
                previewImage.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Called when the view is created.
     * Initializes UI elements and sets up listeners.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        titleEditText = view.findViewById(R.id.titleEditText)
        subtitleEditText = view.findViewById(R.id.subtitleEditText)
        selectImageButton = view.findViewById(R.id.selectImageButton)
        saveButton = view.findViewById(R.id.saveButton)

        previewTitle = view.findViewById(R.id.previewTitle)
        previewSubtitle = view.findViewById(R.id.previewSubtitle)
        previewImage = view.findViewById(R.id.previewImage)

        // Set up text change listeners for the title and subtitle EditTexts
        titleEditText.addTextChangedListener {
            previewTitle.text = it.toString()
        }

        subtitleEditText.addTextChangedListener {
            previewSubtitle.text = it.toString()
        }

        // Set click listener for the select image button
        selectImageButton.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) -> {
                    openImagePicker()
                }

                else -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                    } else {
                        requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            }
        }

        // Set click listener for the save button
        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val subtitle = subtitleEditText.text.toString()

            if (title.isEmpty() || subtitle.isEmpty() || selectedImageUri == null) {
                Snackbar.make(
                    requireView(),
                    "Please fill all the fields",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val imageUri = copyImageToInternalStorage(selectedImageUri!!).toString()

            // Create a new story and insert it into the database
            val newStory = StoryEntity(
                title = title,
                subtitle = subtitle,
                imageUri = imageUri,
                storyDetails = "Some details"
            )

            storyViewModel.insert(newStory)

            // Navigate back to the notebook
            val action = NewStoryFragmentDirections.actionNewStoryFragmentToNotebookFragment()
            findNavController().navigate(action)

            // Snackbar to show the user that the story was saved
            Snackbar.make(
                requireView(),
                "Story saved ✅",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Copies an image from a temporary location to the internal storage.
     *
     * @param contentUri The URI of the image in the temporary location.
     * @return The URI of the image in the internal storage.
     */
    private fun copyImageToInternalStorage(contentUri: Uri): Uri {
        val inputStream: InputStream =
            requireContext().contentResolver.openInputStream(contentUri)!!
        val newFile =
            File(requireContext().filesDir, "JourneyImages/${System.currentTimeMillis()}.jpg")

        // Create a new file in the internal storage and copy the image from the gallery to the
        // internal storage.
        newFile.parentFile?.mkdirs()

        FileOutputStream(newFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        // ! Close the input stream
        inputStream.close()

        return Uri.fromFile(newFile)
    }
}