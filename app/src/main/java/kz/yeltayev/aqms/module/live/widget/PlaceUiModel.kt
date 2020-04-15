package kz.yeltayev.aqms.module.live.widget

import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.model.Place
import kz.yeltayev.aqms.utils.round
import kz.yeltayev.aqms.utils.toKiloPascal
import java.io.Serializable

class PlaceUiModel(
    val place: Place,
    val position: Int = 0
) : Serializable {

    val placeLabel: String
        get() {
            return place.city + ", " + place.country
        }

    val temperatureLabel: String
        get() {
            val value = place.weather.temperature.round()
            return "$value°"
        }

    val humidityLabel: String
        get() {
            val value = place.weather.humidity.round()
            return "$value%"
        }

    val pressureLabel: String
        get() {
            val value = place.weather.pressure.toKiloPascal().round()
            return "$value kPa"
        }

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

    val aqiInfo: Int
        get() {
            when (place.aqi) {
                in 1..50 -> {
                    return R.string.info_aqi_good
                }
                in 51..100 -> {
                    return R.string.info_aqi_moderate
                }
                in 101..150 -> {
                    return R.string.info_aqi_unhealthy_for_sensitive
                }
                in 151..200 -> {
                    return R.string.info_aqi_unhealthy
                }
                in 201..300 -> {
                    return R.string.info_aqi_very_unhealthy
                }
                else -> {
                    return R.string.info_aqi_hazardous
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

    val roundedBackground: Int
        get() {
            when (place.aqi) {
                in 1..50 -> {
                    return R.drawable.shape_rounded_good
                }
                in 51..100 -> {
                    return R.drawable.shape_rounded_moderate
                }
                in 101..150 -> {
                    return R.drawable.shape_rounded_unhealthy_for_sensitive
                }
                in 151..200 -> {
                    return R.drawable.shape_rounded_unhealthy
                }
                in 201..300 -> {
                    return R.drawable.shape_rounded_very_unhealthy
                }
                else -> {
                    return R.drawable.shape_rounded_hazardous
                }
            }
        }

    val topRoundedBackground: Int
        get() {
            when (place.aqi) {
                in 1..50 -> {
                    return R.drawable.shape_top_rounded_good
                }
                in 51..100 -> {
                    return R.drawable.shape_top_rounded_moderate
                }
                in 101..150 -> {
                    return R.drawable.shape_top_rounded_unhealthy_for_sensitive
                }
                in 151..200 -> {
                    return R.drawable.shape_top_rounded_unhealthy
                }
                in 201..300 -> {
                    return R.drawable.shape_top_rounded_very_unhealthy
                }
                else -> {
                    return R.drawable.shape_top_rounded_hazardous
                }
            }
        }
}