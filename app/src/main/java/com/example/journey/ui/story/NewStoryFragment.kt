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
import com.example.journey.R
import com.example.journey.model.StoryEntity

class NewStoryFragment : Fragment(R.layout.fragment_new_story) {

    private lateinit var titleEditText: EditText
    private lateinit var subtitleEditText: EditText
    private lateinit var selectImageButton: Button
    private lateinit var storyImageView: ImageView
    private lateinit var saveButton: Button

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    private val storyViewModel: StoryViewModel by viewModels { StoryViewModel.Factory }

    // ? This is the image uri that will be saved in the database. It's already present in the
    // ? user mobile phone and we just have to retrieve it.
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ? The user can select an image from the gallery and we need to retrieve it. We use
        // ? implicit intent to do that so the user can choose the app he wants to use to select.
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleEditText = view.findViewById(R.id.titleEditText)
        subtitleEditText = view.findViewById(R.id.subtitleEditText)
        selectImageButton = view.findViewById(R.id.selectImageButton)
        storyImageView = view.findViewById(R.id.storyImageView)
        saveButton = view.findViewById(R.id.saveButton)

        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val subtitle = subtitleEditText.text.toString()
            val imageUri = selectedImageUri.toString()

            val newStory = StoryEntity(
                title = title,
                subtitle = subtitle,
                imageUri = imageUri,
                storyDetails = "Some details"
            )

            storyViewModel.insert(newStory)
        }
    }
}