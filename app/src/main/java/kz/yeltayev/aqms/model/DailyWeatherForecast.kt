package kz.yeltayev.aqms.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class DailyWeatherForecast(
    @SerializedName("min_temp")
    val minTemperature: BigDecimal,

    @SerializedName("max_temp")
    val maxTemperature: BigDecimal,

    @SerializedName("wind_dir")
    val windDirection: Int,

    @SerializedName("wind_spd")
    val windSpeed: BigDecimal,

    val datetime: String
)