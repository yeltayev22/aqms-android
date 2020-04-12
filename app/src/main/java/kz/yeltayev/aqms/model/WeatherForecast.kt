package kz.yeltayev.aqms.model

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("city_name")
    val cityName: String,

    val data: List<DailyWeatherForecast>
)