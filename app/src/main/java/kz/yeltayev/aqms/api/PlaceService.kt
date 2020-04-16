package kz.yeltayev.aqms.api

import io.reactivex.Single
import kz.yeltayev.aqms.model.Gas
import kz.yeltayev.aqms.model.Place
import kz.yeltayev.aqms.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceService {

    @GET("places")
    fun fetchPlaces(): Single<Response<List<Place>>>

    @GET("weathers/{placeId}")
    fun fetchWeatherById(@Path("placeId") placeId: Long): Single<Response<List<Weather>>>

    @GET("gases/{placeId}")
    fun fetchGasesById(@Path("placeId") placeId: Long): Single<Response<List<Gas>>>
}