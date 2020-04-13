package kz.yeltayev.aqms.utils

class GeneralPreferences(
    private val localStorage: LocalStorageImpl
) {

    fun addPlace(placeId: Long) {
        val mySavedPlaces: List<String> = getMyPlaces()
        val places = mutableListOf<String>()

        if (mySavedPlaces.isEmpty()) {
            places.add(placeId.toString())
        } else {
            places.addAll(mySavedPlaces)
            places.add(placeId.toString())
        }

        localStorage.putStringList(AQMS_MY_PLACES, places, true)
    }

    fun removePlace(placeId: Long) {
        val mySavedPlaces: List<String> = getMyPlaces()
        val places = mutableListOf<String>()

        if (mySavedPlaces.contains(placeId.toString())) {
            places.addAll(mySavedPlaces.filter { it != placeId.toString() })
        }

        localStorage.putStringList(AQMS_MY_PLACES, places, true)
    }

    fun getMyPlaces(): List<String> {
        return localStorage.getStringList(AQMS_MY_PLACES)
    }

    companion object {
        const val AQMS_MY_PLACES = "AQMS_MY_PLACES"
    }
}