package org.wit.kid.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.kid.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "kids.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<KidModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class KidJSONStore (private val context: Context) : KidStore {

    var kids = mutableListOf<KidModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<KidModel> {
        logAll()
        return kids
    }

    override fun create(kid: KidModel) {
        kid.id = generateRandomId()
        kids.add(kid)
        serialize()
    }


    override fun update(kid: KidModel) {
        val kidsList = findAll() as ArrayList<KidModel>
        var foundKid: KidModel? = kidsList.find { p -> p.id == kid.id }
        if (foundKid != null) {
            foundKid.name = kid.name
            foundKid.age = kid.age
            foundKid.image = kid.image

        }
        serialize()
    }

    override fun delete(kid: KidModel) {
        kids.remove(kid)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(kids, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        kids = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        kids.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
