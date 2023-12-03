package com.example.journey.ui.story

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.room.util.copy
import com.example.journey.R
import com.example.journey.model.StoryEntity
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Fragment for creating a new story.
 */
class NewStoryFragment : Fragment(R.layout.fragment_new_story) {

    // UI elements
    private lateinit var titleEditText: EditText
    private lateinit var subtitleEditText: EditText
    private lateinit var selectImageButton: Button
    private lateinit var storyImageView: ImageView
    private lateinit var saveButton: Button

    // Launcher for picking an image from the gallery
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    // ViewModel for handling story data
    private val storyViewModel: StoryViewModel by activityViewModels { StoryViewModel.Factory }

    // URI of the selected image
    private var selectedImageUri: Uri? = null

    /**
     * Called when the fragment is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register a callback for picking an image from the gallery
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                selectedImageUri = result.data?.data
                storyImageView.setImageURI(selectedImageUri)
                storyImageView.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Called when the view is created.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        titleEditText = view.findViewById(R.id.titleEditText)
        subtitleEditText = view.findViewById(R.id.subtitleEditText)
        selectImageButton = view.findViewById(R.id.selectImageButton)
        storyImageView = view.findViewById(R.id.storyImageView)
        saveButton = view.findViewById(R.id.saveButton)

        // Set click listener for the select image button
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        // Set click listener for the save button
        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val subtitle = subtitleEditText.text.toString()
            val imageUri = copyImageToInternalStorage(selectedImageUri!!).toString()

            // Create a new story and insert it into the database
            val newStory = StoryEntity(
                title = title,
                subtitle = subtitle,
                imageUri = imageUri,
                storyDetails = "Some details"
            )

            storyViewModel.insert(newStory)
        }
    }

    /**
     * Copies an image from a temporary location to the internal storage.
     *
     * @param contentUri The URI of the image in the temporary location.
     * @return The URI of the image in the internal storage.
     */
    private fun copyImageToInternalStorage(contentUri: Uri): Uri {
        val inputStream: InputStream = requireContext().contentResolver.openInputStream(contentUri)!!
        val newFile = File(requireContext().filesDir, "JourneyImages/${System.currentTimeMillis()}.jpg")

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