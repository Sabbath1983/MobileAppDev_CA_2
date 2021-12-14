package org.wit.kid.models

interface KidStore {

    fun findAll(): List<KidModel>
    fun create(kid: KidModel)
    fun update(kid: KidModel)
    fun delete(kid: KidModel)
}