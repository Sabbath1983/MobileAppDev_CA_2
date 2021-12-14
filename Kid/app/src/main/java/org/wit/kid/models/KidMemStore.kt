package org.wit.kid.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class KidMemStore: KidStore {

    val kids = ArrayList<KidModel>()

    override fun findAll(): List<KidModel> {
        return kids
    }

    override fun create(kid: KidModel) {
        kids.add(kid)
        logAll()
    }

    override fun update(kid: KidModel) {
        var foundKid: KidModel? = kids.find { p -> p.id == kid.id }
        if (foundKid != null) {
            foundKid.name = kid.name
            foundKid.age = kid.age
            foundKid.image = kid.image
            logAll()
        }
    }

    override fun delete(kid: KidModel) {
        kids.remove(kid)
    }

    fun logAll() {
        kids.forEach{ i("${it}") }
    }

}