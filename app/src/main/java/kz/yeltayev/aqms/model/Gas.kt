package kz.yeltayev.aqms.model

import java.math.BigDecimal

data class Gas(
    val id: Long,
    val hydrogen: BigDecimal,
    val carbonMonoxide: BigDecimal,
    val ammonia: BigDecimal,
    val h2s: BigDecimal,
    val dateTime: String
)