package kz.yeltayev.aqms.model

import java.math.BigDecimal

data class Place(
    val id: Long,
    val lon: BigDecimal,
    val lat: BigDecimal,
    val city: String,
    val country: String,
    val aqi: Int
)