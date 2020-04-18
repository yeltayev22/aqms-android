package kz.yeltayev.aqms.model

import java.math.BigDecimal

data class Place(
    val id: Long,
    val longitude: BigDecimal,
    val latitude: BigDecimal,
    val city: String,
    val country: String,
    val aqi: Int,
    val gas: Gas,
    val weather: Weather,
    val accessCode: String
)