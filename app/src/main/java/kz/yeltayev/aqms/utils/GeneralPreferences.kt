package kz.yeltayev.aqms.utils

import kz.yeltayev.aqms.model.Place

class GeneralPreferences(
    private val localStorage: LocalStorage
) {

    fun saveMyPlaces(places: List<Place>) {
        localStorage.putStringList(AQMS_MY_PLACES, places.map { it.id.toString() }, true)
    }

    fun getMyPlaces(): List<String> {
        return localStorage.getStringList(AQMS_MY_PLACES)
    }

    companion object {
        const val AQMS_MY_PLACES = "AQMS_MY_PLACES"
    }
}