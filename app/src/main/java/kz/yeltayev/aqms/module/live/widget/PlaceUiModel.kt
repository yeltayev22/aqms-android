package kz.yeltayev.aqms.module.live.widget

import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.model.Place
import kz.yeltayev.aqms.utils.ResourceProvider

class PlaceUiModel(
    val place: Place,
    private val res: ResourceProvider
) {

    val aqiImage: Int
        get() {
            when (place.aqi) {
                in 1..50 -> {
                    return R.drawable.ic_aqi_good
                }
                in 51..100 -> {
                    return R.drawable.ic_aqi_moderate
                }
                in 101..150 -> {
                    return R.drawable.ic_aqi_unhealthy_for_sensitive
                }
                in 151..200 -> {
                    return R.drawable.ic_aqi_unhealthy
                }
                in 201..300 -> {
                    return R.drawable.ic_aqi_very_unhealthy
                }
                else -> {
                    return R.drawable.ic_aqi_hazardous
                }
            }
        }

    val aqiLabel: Int
        get() {
            when (place.aqi) {
                in 1..50 -> {
                    return R.string.label_aqi_good
                }
                in 51..100 -> {
                    return R.string.label_aqi_moderate
                }
                in 101..150 -> {
                    return R.string.label_aqi_unhealthy_for_sensitive
                }
                in 151..200 -> {
                    return R.string.label_aqi_unhealthy
                }
                in 201..300 -> {
                    return R.string.label_aqi_very_unhealthy
                }
                else -> {
                    return R.string.label_aqi_hazardous
                }
            }
        }

    val color: Int
        get() {
            when (place.aqi) {
                in 1..50 -> {
                    return R.color.aqi_good
                }
                in 51..100 -> {
                    return R.color.aqi_moderate
                }
                in 101..150 -> {
                    return R.color.aqi_unhealthy_for_sensitive
                }
                in 151..200 -> {
                    return R.color.aqi_unhealthy
                }
                in 201..300 -> {
                    return R.color.aqi_very_unhealthy
                }
                else -> {
                    return R.color.aqi_hazardous
                }
            }
        }
}