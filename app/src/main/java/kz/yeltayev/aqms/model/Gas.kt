package kz.yeltayev.aqms.model

import java.math.BigDecimal

data class Gas(
    val id: Long,
    val tgs2600: BigDecimal,
    val tgs2602: BigDecimal,
    val dateTime: String
)