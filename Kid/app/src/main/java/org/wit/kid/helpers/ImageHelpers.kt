package org.wit.kid.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import org.wit.kid.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_kid_image.toString())
    intentLauncher.launch(chooseFile)
}