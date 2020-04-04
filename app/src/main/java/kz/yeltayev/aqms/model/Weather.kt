package kz.yeltayev.aqms.model

import java.math.BigDecimal

data class Weather(
    val id: Long,
    val temperature: BigDecimal,
    val humidity: BigDecimal,
    val pressure: BigDecimal,
    val dateTime: String
)