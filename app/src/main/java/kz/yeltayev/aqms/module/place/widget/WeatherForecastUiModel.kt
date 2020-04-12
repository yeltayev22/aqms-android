package kz.yeltayev.aqms.module.place.widget

import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.model.DailyWeatherForecast
import kz.yeltayev.aqms.utils.round
import java.text.SimpleDateFormat
import java.util.*

data class WeatherForecastUiModel(
    val dailyWeatherForecast: DailyWeatherForecast,
    val aqi: Int
) {
    val dayTemperature: String
        get() {
            val value = dailyWeatherForecast.maxTemperature.round()
            return "$value°"
        }

    val nightTemperature: String
        get() {
            val value = dailyWeatherForecast.minTemperature.round()
            return "$value°"
        }

    val windSpeed: String
        get() {
            val value = dailyWeatherForecast.windSpeed.round()
            return "${value}m/s"
        }

    val dayName: String
        get() {
            val inFormat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date = inFormat.parse(dailyWeatherForecast.datetime)
            val outFormat = SimpleDateFormat("EEEE")
            return outFormat.format(date)
        }

    val aqiRange: String
        get() {
            when (aqi) {
                in 1..50 -> {
                    return "0-50"
                }
                in 51..100 -> {
                    return "51-100"
                }
                in 101..150 -> {
                    return "101-150"
                }
                in 151..200 -> {
                    return "151-200"
                }
                in 201..300 -> {
                    return "201-300"
                }
                else -> {
                    return "0-50"
                }
            }
        }

    val roundedBackground: Int
        get() {
            when (aqi) {
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
}