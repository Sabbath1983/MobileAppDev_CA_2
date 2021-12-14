package org.wit.kid.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KidModel(var id: Long = 0,
                    var name: String = "",
                    var age: String = "",
                    var image: Uri = Uri.EMPTY) : Parcelable
