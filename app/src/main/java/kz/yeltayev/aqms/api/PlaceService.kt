package kz.yeltayev.aqms.api

import io.reactivex.Single
import kz.yeltayev.aqms.model.Place
import retrofit2.Response
import retrofit2.http.GET

interface PlaceService {

    @GET("places")
    fun fetchPlaces(): Single<Response<List<Place>>>
}