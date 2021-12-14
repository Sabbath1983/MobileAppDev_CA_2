package org.wit.kid.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.kid.R
import org.wit.kid.databinding.ActivityKidBinding
import org.wit.kid.helpers.showImagePicker
import org.wit.kid.main.MainApp
import org.wit.kid.models.KidModel
import timber.log.Timber
import timber.log.Timber.i

class KidActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKidBinding
    var kid = KidModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var edit = false

        binding = ActivityKidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Kid Activity started...")

        if (intent.hasExtra("kid_edit")) {
            edit = true
            kid = intent.extras?.getParcelable("kid_edit")!!
            binding.name.setText(kid.name)
            binding.age.setText(kid.age)
            binding.btnAdd.setText(R.string.save_kid)
            Picasso.get()
                .load(kid.image)
                .into(binding.kidImage)
            if (kid.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_kid_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            kid.name = binding.name.text.toString()
            kid.age = binding.age.text.toString()
            if (kid.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_kid_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.kids.update(kid.copy())
                } else {
                    app.kids.create(kid.copy())
                }
            }
            i("add Button Pressed: $kid")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_kid, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            kid.image = result.data!!.data!!
                            Picasso.get()
                                .load(kid.image)
                                .into(binding.kidImage)
                            binding.chooseImage.setText(R.string.change_kid_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}