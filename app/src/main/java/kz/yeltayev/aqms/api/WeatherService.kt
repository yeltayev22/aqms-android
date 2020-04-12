package kz.yeltayev.aqms.api

import io.reactivex.Single
import kz.yeltayev.aqms.model.DailyWeatherForecast
import kz.yeltayev.aqms.model.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal

interface WeatherService {

    @GET("forecast/daily")
    fun fetchWeekForecast(
        @Query("key") key: String,
        @Query("lat") lat: BigDecimal,
        @Query("lon") lon: BigDecimal,
        @Query("days") days: Int
    ): Single<Response<WeatherForecast>>
}